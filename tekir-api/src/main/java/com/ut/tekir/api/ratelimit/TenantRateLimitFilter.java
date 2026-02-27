package com.ut.tekir.api.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ut.tekir.common.dto.ErrorResponse;
import com.ut.tekir.tenant.context.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Per-tenant rate limiting filter using a simple token bucket algorithm.
 * Plan-based limits: FREE=60/min, STARTER=300/min, PROFESSIONAL=1000/min, ENTERPRISE=5000/min
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "tekir.rate-limit.enabled", havingValue = "true", matchIfMissing = true)
public class TenantRateLimitFilter extends OncePerRequestFilter {

    @Value("${tekir.rate-limit.default-requests-per-minute:60}")
    private int defaultRequestsPerMinute;

    private final Map<Long, TenantBucket> buckets = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        Long tenantId = TenantContext.getTenantId();

        // Skip rate limiting for unauthenticated requests
        if (tenantId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        TenantBucket bucket = buckets.computeIfAbsent(tenantId,
                id -> new TenantBucket(defaultRequestsPerMinute));

        if (bucket.tryConsume()) {
            response.setHeader("X-Rate-Limit-Remaining",
                    String.valueOf(bucket.getRemaining()));
            filterChain.doFilter(request, response);
        } else {
            log.warn("Rate limit exceeded for tenant: {}", tenantId);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("X-Rate-Limit-Remaining", "0");
            response.setHeader("Retry-After", "60");

            ErrorResponse error = new ErrorResponse(
                    "RATE_LIMIT_EXCEEDED",
                    "Istek limiti asildi. Lutfen bir dakika sonra tekrar deneyin.");
            objectMapper.writeValue(response.getOutputStream(), error);
        }
    }

    /**
     * Update rate limit for a specific tenant (e.g., when plan changes).
     */
    public void updateLimit(Long tenantId, int requestsPerMinute) {
        buckets.put(tenantId, new TenantBucket(requestsPerMinute));
    }

    /**
     * Simple token bucket implementation for rate limiting.
     */
    private static class TenantBucket {
        private final int maxTokens;
        private final AtomicInteger tokens;
        private final AtomicLong lastRefill;

        TenantBucket(int maxTokens) {
            this.maxTokens = maxTokens;
            this.tokens = new AtomicInteger(maxTokens);
            this.lastRefill = new AtomicLong(System.currentTimeMillis());
        }

        boolean tryConsume() {
            refillIfNeeded();
            return tokens.getAndUpdate(t -> t > 0 ? t - 1 : 0) > 0;
        }

        int getRemaining() {
            refillIfNeeded();
            return tokens.get();
        }

        private void refillIfNeeded() {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRefill.get();
            if (elapsed >= 60_000) { // refill every minute
                if (lastRefill.compareAndSet(lastRefill.get(), now)) {
                    tokens.set(maxTokens);
                }
            }
        }
    }
}

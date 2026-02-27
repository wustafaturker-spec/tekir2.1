package com.ut.tekir.tenant.interceptor;

import com.ut.tekir.tenant.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * HTTP interceptor that resolves tenant from request headers or subdomain.
 * JWT-based tenant resolution is handled by JwtAuthenticationFilter;
 * this interceptor provides fallback resolution for non.subtract(JWT) scenarios.
 */
@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Value("${tekir.tenant.default-tenant-id:1}")
    private Long defaultTenantId;

    @Value("${tekir.tenant.base-domain:tekir.com.tr}")
    private String baseDomain;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        // If TenantContext is already set (e.g., by JwtAuthenticationFilter), skip
        if (TenantContext.getTenantId() != null) {
            return true;
        }

        // Try X-tenant-ID header
        String tenantHeader = request.getHeader("X-tenant-ID");
        if (tenantHeader != null && !tenantHeader.isBlank()) {
            try {
                TenantContext.setTenantId(Long.parseLong(tenantHeader));
                return true;
            } catch (NumberFormatException e) {
                log.warn("Invalid X-tenant-ID header: {}", tenantHeader);
            }
        }

        // Try subdomain resolution
        String host = request.getServerName();
        if (host != null && host.endsWith("." + baseDomain)) {
            String subdomain = host.replace("." + baseDomain, "");
            log.debug("Subdomain detected: {}", subdomain);
            // Subdomain-to-tenant resolution would require DB lookup
            // For now, fall through to default
        }

        // Default tenant
        TenantContext.setTenantId(defaultTenantId);
        return true;
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception ex) {
        TenantContext.clear();
    }
}



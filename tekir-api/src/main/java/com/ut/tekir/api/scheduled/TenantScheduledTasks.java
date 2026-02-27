package com.ut.tekir.api.scheduled;

import com.ut.tekir.billing.service.SubscriptionService;
import com.ut.tekir.tenant.entity.Tenant;
import com.ut.tekir.tenant.entity.TenantStatus;
import com.ut.tekir.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Scheduled tasks for tenant lifecycle management.
 * Runs daily to check trial expirations and send warnings.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TenantScheduledTasks {

    private final SubscriptionService subscriptionService;
    private final TenantRepository tenantRepository;

    /**
     * Daily check for expired trial subscriptions.
     * Runs every day at 02:00 AM.
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void checkTrialExpirations() {
        log.info("Running trial expiration check...");

        subscriptionService.expireTrialSubscriptions();

        // Update tenant status for expired subscriptions
        List<Tenant> trialTenants = tenantRepository.findByStatus(TenantStatus.TRIAL);
        int expiredCount = 0;

        for (Tenant tenant : trialTenants) {
            // If the subscription is expired, update tenant status
            try {
                subscriptionService.getActiveSubscription(tenant.getId());
            } catch (Exception e) {
                // No active subscription found-mark as expired
                tenant.setStatus(TenantStatus.EXPIRED);
                tenant.setActive(false);
                tenantRepository.save(tenant);
                expiredCount++;
                log.info("Tenant trial expired: id={}, slug={}", tenant.getId(), tenant.getSlug());
            }
        }

        log.info("Trial expiration check completed. {} tenants expired.", expiredCount);
    }

    /**
     * Send trial expiration warnings (3 days and 1 day before).
     * Runs every day at 09:00 AM.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendTrialExpirationWarnings() {
        log.info("Checking for trial expiration warnings...");
        // TODO: Implement email notifications when email service is available
        // - 3 days before: "Deneme suresinin dolmasina 3 gun kaldi"
        // - 1 day before: "Deneme suresinin dolmasina 1 gun kaldi"
        log.info("Trial expiration warning check completed.");
    }
}



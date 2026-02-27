package com.ut.tekir.billing.service;

import com.ut.tekir.billing.entity.Plan;
import com.ut.tekir.billing.entity.Subscription;
import com.ut.tekir.billing.entity.UsageType;
import com.ut.tekir.billing.exception.PlanLimitExceededException;
import com.ut.tekir.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanLimitEnforcementService {

    private final SubscriptionService subscriptionService;
    private final UsageTrackingService usageTrackingService;

    @Transactional(readOnly = true)
    public void checkLimit(UsageType usageType) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return;
        }
        checkLimit(tenantId, usageType);
    }

    @Transactional(readOnly = true)
    public void checkLimit(Long tenantId, UsageType usageType) {
        Subscription subscription = subscriptionService.getActiveSubscription(tenantId);
        Plan plan = subscription.getPlan();

        Integer limit = getLimitForType(plan, usageType);
        if (plan.isUnlimited(limit)) {
            return; // unlimited
        }

        long currentUsage = usageTrackingService.getCurrentUsage(tenantId, usageType);
        if (currentUsage >= limit) {
            throw new PlanLimitExceededException(
                    usageType.name(),
                    limit,
                    currentUsage,
                    plan.getCode());
        }
    }

    private Integer getLimitForType(Plan plan, UsageType usageType) {
        return switch (usageType) {
            case USERS -> plan.getMaxUsers();
            case INVOICES -> plan.getMaxInvoicesPerMonth();
            case PRODUCTS -> plan.getMaxProducts();
            case CONTACTS -> plan.getMaxContacts();
            case STORAGE_MB -> plan.getMaxStorageMb();
            case API_CALLS -> null; // handled by rate limiter, not plan limits
        };
    }
}

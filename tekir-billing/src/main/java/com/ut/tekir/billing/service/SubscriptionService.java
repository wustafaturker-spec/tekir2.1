package com.ut.tekir.billing.service;

import com.ut.tekir.billing.entity.Plan;
import com.ut.tekir.billing.entity.Subscription;
import com.ut.tekir.billing.entity.SubscriptionStatus;
import com.ut.tekir.billing.exception.SubscriptionExpiredException;
import com.ut.tekir.billing.repository.PlanRepository;
import com.ut.tekir.billing.repository.SubscriptionRepository;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    @Transactional(readOnly = true)
    public Subscription getActiveSubscription(Long tenantId) {
        return subscriptionRepository.findActiveByTenantId(tenantId)
                .orElseThrow(() -> new SubscriptionExpiredException(
                        "Aktif abonelik bulunamadi: tenantId=" + tenantId));
    }

    @Transactional(readOnly = true)
    public Subscription getActiveSubscriptionForCurrentTenant() {
        Long tenantId = TenantContext.getTenantId();
        return getActiveSubscription(tenantId);
    }

    @Transactional
    public Subscription changePlan(Long tenantId, String planCode) {
        Subscription subscription = getActiveSubscription(tenantId);
        Plan newPlan = planRepository.findByCode(planCode)
                .orElseThrow(() -> new EntityNotFoundException("Plan", "code=" + planCode));

        subscription.setPlan(newPlan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        log.info("Plan changed: tenantId={}, newPlan={}", tenantId, planCode);
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void cancelSubscription(Long tenantId) {
        Subscription subscription = getActiveSubscription(tenantId);
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setEndDate(LocalDateTime.now());
        subscriptionRepository.save(subscription);
        log.info("Subscription cancelled: tenantId={}", tenantId);
    }

    @Transactional
    public void expireTrialSubscriptions() {
        List<Subscription> expiredTrials = subscriptionRepository
                .findByStatusAndTrialEndDateBefore(SubscriptionStatus.TRIAL, LocalDateTime.now());

        for (Subscription sub : expiredTrials) {
            sub.setStatus(SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(sub);
            log.info("Trial expired: tenantId={}", sub.getTenantId());
        }
    }

    @Transactional(readOnly = true)
    public List<Subscription> findByTenantId(Long tenantId) {
        return subscriptionRepository.findByTenantId(tenantId);
    }

    @Transactional
    public Subscription extendSubscription(Long subscriptionId, int days) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription", subscriptionId));

        if (subscription.getEndDate() != null) {
            subscription.setEndDate(subscription.getEndDate().plusDays(days));
        }
        if (subscription.getTrialEndDate() != null) {
            subscription.setTrialEndDate(subscription.getTrialEndDate().plusDays(days));
        }
        log.info("Subscription extended: id={}, days={}", subscriptionId, days);
        return subscriptionRepository.save(subscription);
    }
}

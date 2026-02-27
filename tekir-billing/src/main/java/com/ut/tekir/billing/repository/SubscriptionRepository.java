package com.ut.tekir.billing.repository;

import com.ut.tekir.billing.entity.Subscription;
import com.ut.tekir.billing.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByTenantIdAndStatusIn(Long tenantId, List<SubscriptionStatus> statuses);

    default Optional<Subscription> findActiveByTenantId(Long tenantId) {
        return findByTenantIdAndStatusIn(tenantId,
                List.of(SubscriptionStatus.ACTIVE, SubscriptionStatus.TRIAL));
    }

    List<Subscription> findByStatusAndTrialEndDateBefore(SubscriptionStatus status, LocalDateTime date);

    List<Subscription> findByTenantId(Long tenantId);

    long countByStatus(SubscriptionStatus status);
}

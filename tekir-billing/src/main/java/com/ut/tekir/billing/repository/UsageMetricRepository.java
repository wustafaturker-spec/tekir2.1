package com.ut.tekir.billing.repository;

import com.ut.tekir.billing.entity.UsageMetric;
import com.ut.tekir.billing.entity.UsageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsageMetricRepository extends JpaRepository<UsageMetric, Long> {

    Optional<UsageMetric> findByTenantIdAndMetricTypeAndPeriod(
            Long tenantId, UsageType metricType, String period);

    List<UsageMetric> findByTenantIdAndPeriod(Long tenantId, String period);

    List<UsageMetric> findByTenantId(Long tenantId);
}

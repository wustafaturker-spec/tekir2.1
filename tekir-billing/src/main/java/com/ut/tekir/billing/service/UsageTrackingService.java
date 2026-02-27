package com.ut.tekir.billing.service;

import com.ut.tekir.billing.entity.UsageMetric;
import com.ut.tekir.billing.entity.UsageType;
import com.ut.tekir.billing.repository.UsageMetricRepository;
import com.ut.tekir.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsageTrackingService {

    private static final DateTimeFormatter PERIOD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    private final UsageMetricRepository usageMetricRepository;

    @Transactional
    public void incrementUsage(UsageType usageType) {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return;
        }
        incrementUsage(tenantId, usageType);
    }

    @Transactional
    public void incrementUsage(Long tenantId, UsageType usageType) {
        String period = currentPeriod();
        UsageMetric metric = usageMetricRepository
                .findByTenantIdAndMetricTypeAndPeriod(tenantId, usageType, period)
                .orElseGet(() -> UsageMetric.builder()
                        .tenantId(tenantId)
                        .metricType(usageType)
                        .period(period)
                        .currentValue(0L)
                        .build());

        metric.setCurrentValue(metric.getCurrentValue() + 1);
        usageMetricRepository.save(metric);
    }

    @Transactional(readOnly = true)
    public long getCurrentUsage(Long tenantId, UsageType usageType) {
        String period = currentPeriod();
        return usageMetricRepository
                .findByTenantIdAndMetricTypeAndPeriod(tenantId, usageType, period)
                .map(UsageMetric::getCurrentValue)
                .orElse(0L);
    }

    @Transactional(readOnly = true)
    public List<UsageMetric> getMonthlyUsage(Long tenantId, String period) {
        return usageMetricRepository.findByTenantIdAndPeriod(tenantId, period);
    }

    @Transactional(readOnly = true)
    public List<UsageMetric> getAllUsage(Long tenantId) {
        return usageMetricRepository.findByTenantId(tenantId);
    }

    private String currentPeriod() {
        return LocalDate.now().format(PERIOD_FORMAT);
    }
}


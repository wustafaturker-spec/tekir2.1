package com.ut.tekir.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USAGE_METRIC", indexes = {
    @Index(name = "IDX_USAGE_TENANT_TYPE_PERIOD", columnList = "TENANT_ID, METRIC_TYPE, PERIOD")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TENANT_ID", nullable = false)
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "METRIC_TYPE", nullable = false, length = 30)
    private UsageType metricType;

    @Column(name = "CURRENT_VALUE", nullable = false)
    @Builder.Default
    private Long currentValue = 0L;

    @Column(name = "PERIOD", nullable = false, length = 7)
    private String period; // YYYY-MM format

    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        lastUpdated = LocalDateTime.now();
    }
}


package com.ut.tekir.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SUBSCRIPTION", indexes = {
    @Index(name = "IDX_SUBSCRIPTION_TENANT", columnList = "TENANT_ID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TENANT_ID", nullable = false)
    private Long tenantId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAN_ID", nullable = false)
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private SubscriptionStatus status;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "TRIAL_END_DATE")
    private LocalDateTime trialEndDate;

    @Column(name = "BILLING_CYCLE", length = 20)
    @Builder.Default
    private String billingCycle = "MONTHLY";

    @Column(name = "EXTERNAL_SUBSCRIPTION_ID", length = 255)
    private String externalSubscriptionId;

    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.TRIAL;
    }

    public boolean isTrialExpired() {
        return status == SubscriptionStatus.TRIAL
                && trialEndDate != null
                && LocalDateTime.now().isAfter(trialEndDate);
    }
}

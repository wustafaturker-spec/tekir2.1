package com.ut.tekir.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PLAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "MAX_USERS")
    private Integer maxUsers;

    @Column(name = "MAX_INVOICES_PER_MONTH")
    private Integer maxInvoicesPerMonth;

    @Column(name = "MAX_PRODUCTS")
    private Integer maxProducts;

    @Column(name = "MAX_CONTACTS")
    private Integer maxContacts;

    @Column(name = "MAX_STORAGE_MB")
    private Integer maxStorageMb;

    @Column(name = "MONTHLY_PRICE", precision = 10, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(name = "YEARLY_PRICE", precision = 10, scale = 2)
    private BigDecimal yearlyPrice;

    @Column(name = "FEATURES", columnDefinition = "TEXT")
    private String features;

    @Column(name = "TRIAL_DAYS")
    @Builder.Default
    private Integer trialDays = 14;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    /**
     * Returns true if this plan has unlimited usage for the given limit.
     * A null value means unlimited.
     */
    public boolean isUnlimited(Integer limit) {
        return limit == null || limit <= 0;
    }
}

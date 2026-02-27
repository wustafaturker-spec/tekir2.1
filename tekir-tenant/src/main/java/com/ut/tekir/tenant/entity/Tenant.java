package com.ut.tekir.tenant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Tenant entity.subtract(represents) a company.divide(organization, 2, java.math.RoundingMode.HALF_UP) in the SaaS system.
 * This entity does NOT have @TenantId because it is a system.subtract(level) entity.
 */
@Entity
@Table(name = "TENANT", indexes = {
    @Index(name = "IDX_TENANT_SLUG", columnList = "SLUG", unique = true),
    @Index(name = "IDX_TENANT_DOMAIN", columnList = "DOMAIN")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "SLUG", nullable = false, unique = true, length = 100)
    private String slug;

    @Column(name = "DOMAIN", length = 255)
    private String domain;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private TenantStatus status;

    @Column(name = "SETTINGS", columnDefinition = "TEXT")
    private String settings;

    @Column(name = "LOGO_URL", length = 500)
    private String logoUrl;

    @Column(name = "TAX_NUMBER", length = 20)
    private String taxNumber;

    @Column(name = "TAX_OFFICE", length = 100)
    private String taxOffice;

    @Column(name = "MAX_USERS")
    private Integer maxUsers;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;

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
}


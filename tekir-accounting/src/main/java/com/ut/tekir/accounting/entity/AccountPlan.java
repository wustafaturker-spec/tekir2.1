package com.ut.tekir.accounting.entity;

import com.ut.tekir.accounting.enums.AccountPlanType;
import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_PLAN")
@Getter
@Setter
public class AccountPlan extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, length = 20)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "PLAN_TYPE", length = 30)
    private AccountPlanType planType = AccountPlanType.CUSTOM;

    @Column(name = "IS_DEFAULT")
    private Boolean isDefault = false;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Column(name = "EFFECTIVE_DATE")
    private LocalDate effectiveDate;

    @Column(name = "ACCOUNT_COUNT")
    private Integer accountCount = 0;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChartAccount> accounts = new ArrayList<>();
}

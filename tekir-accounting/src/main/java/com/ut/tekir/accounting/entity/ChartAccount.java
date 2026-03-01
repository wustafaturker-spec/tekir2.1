package com.ut.tekir.accounting.entity;

import com.ut.tekir.accounting.enums.AccountType;
import com.ut.tekir.accounting.enums.NormalBalance;
import com.ut.tekir.common.entity.AuditBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CHART_ACCOUNT")
@Getter
@Setter
public class ChartAccount extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ID", nullable = false)
    private AccountPlan plan;

    @Column(name = "CODE", nullable = false, length = 20)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 20, nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "NORMAL_BALANCE", length = 10, nullable = false)
    private NormalBalance normalBalance;

    @Column(name = "LEVEL", nullable = false)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private ChartAccount parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderBy("code ASC")
    private List<ChartAccount> children = new ArrayList<>();

    @Column(name = "IS_DETAIL")
    private Boolean isDetail = false;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Column(name = "CURRENCY", length = 3)
    private String currency;

    @Column(name = "TAX_CODE", length = 10)
    private String taxCode;

    @Column(name = "IS_BLOCKED")
    private Boolean isBlocked = false;
}

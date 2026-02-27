package com.ut.tekir.common.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.enums.OpportunityStage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Satış fırsatı / pipeline kaydı.
 * Her Contact için birden fazla fırsat olabilir.
 */
@Entity
@Table(name = "CONTACT_OPPORTUNITY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ContactOpportunity extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    @NotNull
    private Contact contact;

    @Column(name = "TITLE", nullable = false, length = 200)
    @NotBlank
    @Size(max = 200)
    private String title;

    @Column(name = "STAGE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private OpportunityStage stage = OpportunityStage.PROSPECT;

    @Column(name = "EXPECTED_REVENUE", precision = 18, scale = 2)
    private BigDecimal expectedRevenue = BigDecimal.ZERO;

    @Column(name = "CURRENCY", length = 3)
    @Size(max = 3)
    private String currency = "TRY";

    @Column(name = "PROBABILITY")
    @Min(0)
    @Max(100)
    private Integer probability = 0;

    @Column(name = "EXPECTED_CLOSE_DATE")
    private LocalDate expectedCloseDate;

    @Column(name = "ACTUAL_CLOSE_DATE")
    private LocalDate actualCloseDate;

    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;

    @Column(name = "ASSIGNED_TO", length = 100)
    @Size(max = 100)
    private String assignedTo;

    @Column(name = "ACTIVE")
    private Boolean active = Boolean.TRUE;
}

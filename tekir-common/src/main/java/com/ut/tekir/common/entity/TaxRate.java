package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.TaxKind;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Tax rate entity with date range, rate.divide(amount, 2, java.math.RoundingMode.HALF_UP)/fraction support, and withholding fields.
 */
@Entity
@Table(name = "TAX_RATE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class TaxRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TAX_ID")
    private Tax tax;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "KIND")
    @Enumerated(EnumType.ORDINAL)
    private TaxKind kind = TaxKind.Rate;

    @Column(name = "RATE", precision = 10, scale = 2)
    private BigDecimal rate = BigDecimal.ZERO;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "CCYVAL")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "LCYVAL"))
    })
    private MoneySet amount = new MoneySet();

    @Column(name = "WITHHOLDING_KIND")
    @Enumerated(EnumType.ORDINAL)
    private TaxKind withholdingKind;

    @Column(name = "WITHHOLDING_AMOUNT", precision = 10, scale = 2)
    private BigDecimal withholdingAmount = BigDecimal.ZERO;

    @Column(name = "WITHHOLDING_RATE", precision = 10, scale = 2)
    private BigDecimal withholdingRate = BigDecimal.ZERO;
}


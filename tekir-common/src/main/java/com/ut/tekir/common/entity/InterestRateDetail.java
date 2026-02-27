package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "INTEREST_RATE_DETAIL") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class InterestRateDetail extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @ManyToOne @JoinColumn(name = "INTEREST_RATE_ID") private InterestRate interestRate;
    @Column(name = "FROM_DATE") private LocalDate fromDate;
    @Column(name = "RATE", precision = 5, scale = 2) private BigDecimal rate;
}

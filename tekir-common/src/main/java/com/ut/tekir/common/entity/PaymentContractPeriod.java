package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Payment contract period entity.
 */
@Entity
@Table(name = "PAYMENT_CONTRACT_PERIOD")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PaymentContractPeriod extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_CONTRACT_ID")
    private PaymentContract paymentContract;

    @Column(name = "PERIOD_DAY")
    private Integer periodDay;

    @Column(name = "RATE", precision = 5, scale = 2)
    private BigDecimal rate;
}

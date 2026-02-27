package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Payment commission entity.
 */
@Entity
@Table(name = "PAYMENT_COMMISSION")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PaymentCommission extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "RATE", precision = 5, scale = 2)
    private BigDecimal rate;

    @Column(name = "INFO")
    private String info;
}

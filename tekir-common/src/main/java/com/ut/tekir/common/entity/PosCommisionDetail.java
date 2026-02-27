package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * POS Commission detail entity.
 */
@Entity
@Table(name = "POS_COMMISION_DETAIL")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PosCommisionDetail extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private PosCommision owner;

    @Column(name = "INSTALLMENT_COUNT")
    private Integer installmentCount;

    @Column(name = "RATE", precision = 5, scale = 2)
    private BigDecimal rate;
}

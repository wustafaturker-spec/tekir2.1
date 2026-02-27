package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Interest currency entity.
 */
@Entity
@Table(name = "INTEREST_CURRENCY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class InterestCurrency extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INTEREST_ID")
    private Interest interest;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;
}

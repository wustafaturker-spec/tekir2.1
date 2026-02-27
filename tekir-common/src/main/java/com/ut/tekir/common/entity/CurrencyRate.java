package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Currency rate entity: stores exchange rates for a currency pair on a given date.
 */
@Entity
@Table(name = "CURRENCY_RATE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class CurrencyRate extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "RATE_DATE")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_PAIR")
    private CurrencyPair currencyPair;

    @Column(name = "BID", precision = 10, scale = 4)
    private BigDecimal bid = BigDecimal.ZERO;

    @Column(name = "ASK", precision = 10, scale = 4)
    private BigDecimal ask = BigDecimal.ZERO;

    @Column(name = "BANKNOTE_BUYING", precision = 10, scale = 4)
    private BigDecimal bankNoteBuying = BigDecimal.ZERO;

    @Column(name = "BANKNOTE_SELLING", precision = 10, scale = 4)
    private BigDecimal bankNoteSelling = BigDecimal.ZERO;
}

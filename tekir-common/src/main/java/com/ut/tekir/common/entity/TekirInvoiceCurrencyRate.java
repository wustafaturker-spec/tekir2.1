package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity @Table(name = "TEKIR_INVOICE_CURRENCY_RATE") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class TekirInvoiceCurrencyRate extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @ManyToOne @JoinColumn(name = "OWNER_ID") private TekirInvoice owner;
    @ManyToOne @JoinColumn(name = "CURRENCY_PAIR_ID") private CurrencyPair currencyPair;
    @Column(name = "BID", precision = 19, scale = 6) private BigDecimal bid;
    @Column(name = "ASK", precision = 19, scale = 6) private BigDecimal ask;
}

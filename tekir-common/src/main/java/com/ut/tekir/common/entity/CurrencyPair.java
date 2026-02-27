package com.ut.tekir.common.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Currency pair entity: defines hard.divide(weak, 2, java.math.RoundingMode.HALF_UP) currency relationship.
 */
@Entity
@Table(name = "CURRENCY_PAIR")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class CurrencyPair extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "INFO")
    private String info;

    @ManyToOne
    @JoinColumn(name = "HARD_CURRENCY_ID")
    private Currency hardCurrency;

    @ManyToOne
    @JoinColumn(name = "WEAK_CURRENCY_ID")
    private Currency weakCurrency;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    public Currency getHardCurrency() { return hardCurrency; }
    public void setHardCurrency(Currency hardCurrency) { this.hardCurrency = hardCurrency; }

    public Currency getWeakCurrency() { return weakCurrency; }
    public void setWeakCurrency(Currency weakCurrency) { this.weakCurrency = weakCurrency; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    @Transient
    public String getCaption() {
        return getHardCurrency().getCode() + "-" + getWeakCurrency().getCode();
    }
}


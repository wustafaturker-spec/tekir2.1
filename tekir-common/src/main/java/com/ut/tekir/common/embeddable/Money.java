package com.ut.tekir.common.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

import com.ut.tekir.common.BaseConsts;
import lombok.Getter;
import lombok.Setter;

/**
 * Base money type for the application.
 * Preserves column mapping: CCY (3-char ISO), CCYVAL (precision 19, scale 2).
 *
 * IMPORTANT: Ordinal values and column names must match legacy database exactly.
 */
@Embeddable
@MappedSuperclass
@Getter
@Setter
public class Money implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CCY", length = 3)
    private String currency = BaseConsts.SYSTEM_CURRENCY_CODE;

    @Column(name = "CCYVAL", precision = 19, scale = 2)
    private BigDecimal value = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }


    public Money() {
    }

    public Money(Money money) {
        this.currency = money.getCurrency();
        this.value = money.getValue();
    }

    public Money(String currency) {
        this.currency = currency;
    }

    public Money(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money(BigDecimal value) {
        this.value = value;
    }

    public void clearMoney() {
        this.currency = BaseConsts.SYSTEM_CURRENCY_CODE;
        this.value = BigDecimal.ZERO;
    }

    public void add(Money money) {
        currencyValidation(money);
        value = value.add(money.getValue());
    }

    public void subtract(Money money) {
        currencyValidation(money);
        value = value.subtract(money.getValue());
    }

    public void multiply(Money money) {
        currencyValidation(money);
        value = value.multiply(money.getValue());
    }

    public void divide(Money money) {
        currencyValidation(money);
        value = value.divide(money.getValue(), 2, RoundingMode.HALF_UP);
    }

    public void currencyValidation(Money money) {
        if (!currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException(
                "Currency mismatch: " + currency + " vs " + money.getCurrency());
        }
    }

    @Override
    public String toString() {
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(2);
        f.setMinimumFractionDigits(2);
        return f.format(value) + " " + currency;
    }
}



package com.ut.tekir.common.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import com.ut.tekir.common.BaseConsts;
import lombok.Getter;
import lombok.Setter;

/**
 * Dual-currency money type: tracks both foreign currency value and local currency value.
 * Preserves column mapping: CCY, CCYVAL (from Money parent), LCYVAL (local amount).
 */
@Embeddable
@Getter
@Setter
public class MoneySet extends Money implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "LCYVAL", precision = 19, scale = 2)
    private BigDecimal localAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public BigDecimal getLocalAmount() { return localAmount; }
    public void setLocalAmount(BigDecimal localAmount) { this.localAmount = localAmount; }


    public MoneySet() {
        super();
    }

    public MoneySet(String currency) {
        super(currency);
    }

    public MoneySet(BigDecimal ccyAmt, BigDecimal localAmt, String currency) {
        super(ccyAmt, currency);
        this.localAmount = localAmt;
    }

    public MoneySet(BigDecimal ccyAmt, BigDecimal localAmt) {
        super(ccyAmt);
        this.localAmount = localAmt;
    }

    public MoneySet(MoneySet amount) {
        super(amount.getValue(), amount.getCurrency());
        this.localAmount = amount.getLocalAmount();
    }

    @Override
    public void clearMoney() {
        setCurrency(BaseConsts.SYSTEM_CURRENCY_CODE);
        setLocalAmount(BigDecimal.ZERO);
        setValue(BigDecimal.ZERO);
    }

    public void moveFieldsOf(MoneySet other) {
        setCurrency(other.getCurrency());
        setLocalAmount(other.getLocalAmount());
        setValue(other.getValue());
    }

    public MoneySet add(MoneySet moneySet) {
        super.add(moneySet);
        localAmount = localAmount.add(moneySet.getLocalAmount());
        return this;
    }

    public MoneySet subtract(MoneySet moneySet) {
        super.subtract(moneySet);
        localAmount = localAmount.subtract(moneySet.getLocalAmount());
        return this;
    }

    public MoneySet divide(BigDecimal divisor, int scale) {
        setValue(getValue().divide(divisor, scale, RoundingMode.HALF_UP));
        localAmount = localAmount.divide(divisor, scale, RoundingMode.HALF_UP);
        return this;
    }

    public MoneySet multiply(BigDecimal multiplicand) {
        setValue(getValue().multiply(multiplicand));
        setLocalAmount(getLocalAmount().multiply(multiplicand));
        return this;
    }
}



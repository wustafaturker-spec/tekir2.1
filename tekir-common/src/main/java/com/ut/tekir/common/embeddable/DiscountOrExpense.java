package com.ut.tekir.common.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.BaseConsts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Discount or expense embeddable used in tender.divide(invoice, 2, java.math.RoundingMode.HALF_UP)/order detail lines.
 * Preserves exact column mapping from old Tekir.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DiscountOrExpense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PERCENTAGE")
    private Boolean percentage = Boolean.TRUE;

    @Column(name = "RATE")
    private BigDecimal rate = BigDecimal.ZERO;

    @Column(name = "CCY", length = 3)
    @Size(max = 3)
    private String currency = BaseConsts.SYSTEM_CURRENCY_CODE;

    @Column(name = "CCYVAL", precision = 10, scale = 2)
    private BigDecimal value = BigDecimal.ZERO;

    @Column(name = "LCYVAL", precision = 10, scale = 2)
    private BigDecimal localAmount = BigDecimal.ZERO;

    public Boolean getPercentage() { return percentage; }
    public void setPercentage(Boolean percentage) { this.percentage = percentage; }
    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public BigDecimal getLocalAmount() { return localAmount; }
    public void setLocalAmount(BigDecimal localAmount) { this.localAmount = localAmount; }
    

    public MoneySet getAsMoney() {
        return new MoneySet(value, localAmount, currency);
    }

    public void moveFieldsOf(DiscountOrExpense other) {
        if (other != null) {
            this.currency = other.getCurrency();
            this.localAmount = other.getLocalAmount();
            this.rate = other.getRate();
            this.value = other.getValue();
            if (other.getPercentage() != null) {
                this.percentage = other.getPercentage();
            }
        }
    }

    public void clear() {
        this.percentage = Boolean.TRUE;
        this.rate = BigDecimal.ZERO;
        this.currency = BaseConsts.SYSTEM_CURRENCY_CODE;
        this.value = BigDecimal.ZERO;
        this.localAmount = BigDecimal.ZERO;
    }
}


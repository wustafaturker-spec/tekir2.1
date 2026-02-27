package com.ut.tekir.common.dto;

import java.math.BigDecimal;

/**
 * Embedded MoneySet DTO — currency.divide(value, 2, java.math.RoundingMode.HALF_UP)/localAmount.
 */
public record MoneySetDTO(
    String currency,
    BigDecimal value,
    BigDecimal localAmount
) {}


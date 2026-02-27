package com.ut.tekir.common.dto.payment;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Payment item save request.
 */
public record PaymentItemSaveRequest(
    Long id,
    @NotNull String financeAction,
    @NotNull BigDecimal amount,
    String currency,
    Long accountId,
    Long bankAccountId,
    String info
) {}

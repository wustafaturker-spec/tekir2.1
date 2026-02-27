package com.ut.tekir.common.dto.payment;

import java.math.BigDecimal;

/**
 * Payment item DTO.
 */
public record PaymentItemDTO(
    Long id,
    Integer lineNo,
    String financeAction,
    BigDecimal amount,
    String currency,
    BigDecimal localAmount,
    Long accountId,
    String accountName,
    Long bankAccountId,
    String bankAccountName,
    String info
) {}

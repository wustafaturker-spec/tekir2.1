package com.ut.tekir.common.dto.expense;

import java.math.BigDecimal;

/**
 * Expense line item DTO.
 */
public record ExpenseItemDTO(
    Long id,
    Long serviceId,
    String serviceCode,
    String serviceName,
    BigDecimal amount,
    String currency,
    BigDecimal localAmount,
    BigDecimal taxRate,
    String info
) {}

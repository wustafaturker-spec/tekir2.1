package com.ut.tekir.common.dto.order;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemSaveRequest(
    Long id,
    @NotNull Long productId,
    @NotNull BigDecimal quantity,
    Long unitId,
    @NotNull BigDecimal unitPrice,
    String currency,
    BigDecimal taxRate,
    BigDecimal discountRate,
    String info
) {}

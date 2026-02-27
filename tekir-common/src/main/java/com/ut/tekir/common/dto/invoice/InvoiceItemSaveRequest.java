package com.ut.tekir.common.dto.invoice;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Invoice line item save request.
 */
public record InvoiceItemSaveRequest(
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

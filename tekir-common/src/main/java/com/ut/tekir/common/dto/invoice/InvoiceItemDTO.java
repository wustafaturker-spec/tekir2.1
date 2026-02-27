package com.ut.tekir.common.dto.invoice;

import java.math.BigDecimal;

/**
 * Invoice line item DTO.
 */
public record InvoiceItemDTO(
    Long id,
    Integer lineNo,
    Long productId,
    String productCode,
    String productName,
    BigDecimal quantity,
    String unitName,
    BigDecimal unitPrice,
    String currency,
    BigDecimal amount,
    BigDecimal taxRate,
    BigDecimal taxAmount,
    BigDecimal discountRate,
    BigDecimal discountAmount,
    BigDecimal lineTotal,
    String info
) {}

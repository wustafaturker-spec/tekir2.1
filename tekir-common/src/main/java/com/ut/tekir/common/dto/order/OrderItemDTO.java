package com.ut.tekir.common.dto.order;

import java.math.BigDecimal;

/**
 * Order line item DTO.
 */
public record OrderItemDTO(
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
    BigDecimal discountRate,
    BigDecimal lineTotal,
    String info
) {}

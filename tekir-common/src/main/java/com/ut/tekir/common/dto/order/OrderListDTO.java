package com.ut.tekir.common.dto.order;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Order list DTO for paginated views.
 */
public record OrderListDTO(
    Long id,
    String code,
    String serial,
    String reference,
    LocalDate date,
    String tradeAction,
    String orderStatus,
    String contactCode,
    String contactFullname,
    BigDecimal grandTotalValue,
    String grandTotalCurrency,
    String info
) {}

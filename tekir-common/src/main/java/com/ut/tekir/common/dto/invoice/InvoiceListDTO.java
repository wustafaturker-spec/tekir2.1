package com.ut.tekir.common.dto.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Lightweight projection for invoice list views.
 */
public record InvoiceListDTO(
    Long id,
    String code,
    String serial,
    String reference,
    LocalDate date,
    String tradeAction,
    String contactCode,
    String contactFullname,
    String warehouseName,
    BigDecimal grandTotalValue,
    String grandTotalCurrency,
    BigDecimal beforeTaxValue,
    BigDecimal totalTaxValue,
    String info
) {}

package com.ut.tekir.common.dto.invoice;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Full invoice detail projection including line items and financial totals.
 */
public record InvoiceDetailDTO(
    Long id,
    String code,
    String serial,
    String reference,
    LocalDate date,
    String tradeAction,
    // Contact
    Long contactId,
    String contactCode,
    String contactFullname,
    // Warehouse
    Long warehouseId,
    String warehouseName,
    // Account
    Long accountId,
    String accountName,
    // Financial totals
    MoneySetDTO beforeTax,
    MoneySetDTO totalTax,
    MoneySetDTO grandTotal,
    MoneySetDTO totalFee,
    MoneySetDTO totalExpense,
    BigDecimal taxExcludedTotalLcyVal,
    BigDecimal totalDiscountLcyVal,
    BigDecimal totalDocumentDiscountLcyVal,
    // Line items
    List<InvoiceItemDTO> items,
    // Info
    String info,
    // Audit
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}

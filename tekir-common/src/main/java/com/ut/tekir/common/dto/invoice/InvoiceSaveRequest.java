package com.ut.tekir.common.dto.invoice;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Invoice create/update request DTO.
 */
public record InvoiceSaveRequest(
    Long id,
    @NotNull String tradeAction,
    @Size(max = 15) String serial,
    @Size(max = 30) String reference,
    LocalDate date,
    @NotNull Long contactId,
    Long warehouseId,
    Long accountId,
    String info,
    List<InvoiceItemSaveRequest> items
) {}

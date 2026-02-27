package com.ut.tekir.common.dto.spendingnote;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record SpendingNoteSaveRequest(
    Long employeeId,
    @NotNull Long warehouseId,
    String action,
    String info,
    String reference,
    List<SpendingNoteItemSaveRequest> items
) {
    public record SpendingNoteItemSaveRequest(
        Long productId,
        BigDecimal quantity,
        String currency,
        BigDecimal unitPrice,
        String lineCode,
        String info
    ) {}
}

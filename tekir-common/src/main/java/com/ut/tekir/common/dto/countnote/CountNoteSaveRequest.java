package com.ut.tekir.common.dto.countnote;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CountNoteSaveRequest(
    @NotNull Long warehouseId,
    String info,
    String reference,
    List<CountNoteItemSaveRequest> items
) {
    public record CountNoteItemSaveRequest(
        Long productId,
        Integer countQuantity,
        Integer existingQuantity,
        String lineCode,
        String info
    ) {}
}

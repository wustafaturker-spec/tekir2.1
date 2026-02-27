package com.ut.tekir.common.dto.countnote;

public record CountNoteItemDTO(
    Long id,
    Long productId,
    String productName,
    Integer countQuantity,
    Integer existingQuantity,
    String lineCode,
    String info
) {}

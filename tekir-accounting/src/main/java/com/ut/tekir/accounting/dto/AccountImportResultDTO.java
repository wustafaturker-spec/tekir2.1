package com.ut.tekir.accounting.dto;

import java.util.List;

public record AccountImportResultDTO(
        int totalRows,
        int successCount,
        int errorCount,
        List<ImportError> errors
) {
    public record ImportError(int rowNumber, String code, String message) {}
}

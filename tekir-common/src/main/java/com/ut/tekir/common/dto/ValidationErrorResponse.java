package com.ut.tekir.common.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
    String code,
    String message,
    List<FieldError> errors,
    LocalDateTime timestamp
) {
    public ValidationErrorResponse(List<FieldError> errors) {
        this("VALIDATION_ERROR", "Validation failed", errors, LocalDateTime.now());
    }

    public record FieldError(String field, String message) {}
}

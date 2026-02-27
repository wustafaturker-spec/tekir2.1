package com.ut.tekir.common.dto.bank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Bank save request DTO.
 */
public record BankSaveRequest(
    Long id,
    @NotBlank @Size(max = 20) String code,
    @Size(max = 50) String name,
    @Size(max = 20) String swiftCode,
    Boolean active
) {}

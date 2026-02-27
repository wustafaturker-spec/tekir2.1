package com.ut.tekir.common.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Account save request DTO.
 */
public record AccountSaveRequest(
    Long id,
    @NotBlank @Size(max = 20) String code,
    @Size(max = 50) String name,
    String info,
    String accountType,
    @Size(max = 3) String currency,
    Long organizationId
) {}

package com.ut.tekir.common.dto.bank;

import java.time.LocalDateTime;

/**
 * Bank list DTO.
 */
public record BankListDTO(
    Long id,
    String code,
    String name,
    String swiftCode,
    Boolean active
) {}

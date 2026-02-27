package com.ut.tekir.common.dto.bank;

/**
 * Bank branch DTO.
 */
public record BankBranchDTO(
    Long id,
    String code,
    String name,
    String eftCode,
    Boolean active,
    Long bankId
) {}

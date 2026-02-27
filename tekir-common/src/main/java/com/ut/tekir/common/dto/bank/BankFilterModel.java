package com.ut.tekir.common.dto.bank;

/**
 * Filter parameters for bank search.
 */
public record BankFilterModel(
    String code,
    String name,
    Boolean active
) {}

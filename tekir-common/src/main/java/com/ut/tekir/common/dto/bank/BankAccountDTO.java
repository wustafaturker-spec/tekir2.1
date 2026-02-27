package com.ut.tekir.common.dto.bank;

/**
 * Bank account DTO.
 */
public record BankAccountDTO(
    Long id,
    String name,
    String accountNo,
    String iban,
    String currency,
    Long bankBranchId,
    String bankBranchName,
    Long accountId,
    Boolean active
) {}

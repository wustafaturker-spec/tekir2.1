package com.ut.tekir.common.dto.bank;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Full bank detail DTO including branches and accounts.
 */
public record BankDetailDTO(
    Long id,
    String code,
    String name,
    String swiftCode,
    Boolean active,
    List<BankBranchDTO> branches,
    List<BankAccountDTO> accounts,
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate
) {}

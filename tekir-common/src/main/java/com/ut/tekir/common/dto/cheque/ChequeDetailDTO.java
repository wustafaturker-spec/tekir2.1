package com.ut.tekir.common.dto.cheque;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Full cheque detail projection.
 */
public record ChequeDetailDTO(
    Long id,
    String referenceNo,
    String status,
    // Bank info
    Long bankId,
    String bankName,
    Long bankBranchId,
    String branchName,
    String accountNo,
    // Owner
    String chequeOwner,
    // Financial
    MoneySetDTO money,
    LocalDate issueDate,
    LocalDate dueDate,
    // Contact
    Long contactId,
    String contactCode,
    String contactFullname,
    // Info
    String info,
    // Audit
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate,
    String updateUser,
    String direction,
    String chequeNumber
) {}

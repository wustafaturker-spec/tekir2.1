package com.ut.tekir.common.dto.cheque;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Cheque list DTO for paginated views.
 */
public record ChequeListDTO(
    Long id,
    String referenceNo,
    String bankName,
    String branchName,
    String accountNo,
    String chequeOwner,
    String status,
    BigDecimal amount,
    String currency,
    LocalDate dueDate,
    String contactFullname,
    String info,
    String direction,
    String chequeNumber
) {}

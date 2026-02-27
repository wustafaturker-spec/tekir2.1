package com.ut.tekir.common.dto.cheque;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Cheque create/update request.
 */
public record ChequeSaveRequest(
    Long id,
    @Size(max = 50) String bankName,
    @Size(max = 50) String bankBranch,
    @Size(max = 20) String accountNo,
    @Size(max = 50) String iban,
    String chequeOwner,
    @NotNull BigDecimal amount,
    String currency,
    LocalDate maturityDate,
    String paymentPlace,
    String info,
    String direction, // Incoming, Outgoing
    Long contactId,
    String chequeNumber
) {}

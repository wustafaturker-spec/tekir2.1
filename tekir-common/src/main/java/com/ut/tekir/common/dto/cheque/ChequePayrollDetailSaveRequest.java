package com.ut.tekir.common.dto.cheque;

import jakarta.validation.constraints.NotNull;

public record ChequePayrollDetailSaveRequest(
        Long id,
        @NotNull Long chequeId,
        String info) {
}

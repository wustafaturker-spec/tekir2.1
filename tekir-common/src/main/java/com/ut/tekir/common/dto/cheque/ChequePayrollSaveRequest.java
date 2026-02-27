package com.ut.tekir.common.dto.cheque;

import com.ut.tekir.common.enums.PayrollType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record ChequePayrollSaveRequest(
        Long id,
        String serial,
        String reference,
        LocalDate date,
        String info,
        @NotNull PayrollType payrollType,
        Long contactId,
        Long accountId,
        List<ChequePayrollDetailSaveRequest> details) {
}

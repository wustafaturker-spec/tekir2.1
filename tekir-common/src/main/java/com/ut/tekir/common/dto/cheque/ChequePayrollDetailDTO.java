package com.ut.tekir.common.dto.cheque;

import com.ut.tekir.common.enums.PayrollType;
import java.time.LocalDate;
import java.util.List;

public record ChequePayrollDetailDTO(
        Long id,
        String code,
        String serial,
        String reference,
        LocalDate date,
        String info,
        PayrollType payrollType,
        Long contactId,
        String contactName,
        Long accountId,
        String accountName,
        List<ChequeListDTO> cheques) {
}

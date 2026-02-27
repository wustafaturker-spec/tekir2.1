package com.ut.tekir.common.dto.depositaccount;

import java.time.LocalDate;

public record DepositAccountListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String bankAccountName,
    String info
) {}

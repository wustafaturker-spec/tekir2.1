package com.ut.tekir.common.dto.debitcredit;

import java.time.LocalDate;

public record DebitCreditVirementListDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String info
) {}

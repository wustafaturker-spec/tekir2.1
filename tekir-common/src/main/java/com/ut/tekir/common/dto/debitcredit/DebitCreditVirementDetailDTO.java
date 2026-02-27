package com.ut.tekir.common.dto.debitcredit;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DebitCreditVirementDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String info,
    String reference,
    List<VirementItemDTO> items,
    LocalDateTime createDate,
    String createUser
) {
    public record VirementItemDTO(
        Long id,
        MoneySetDTO amount,
        String info
    ) {}
}

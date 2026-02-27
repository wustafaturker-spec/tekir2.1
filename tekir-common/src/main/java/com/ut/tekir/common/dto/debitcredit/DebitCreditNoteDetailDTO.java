package com.ut.tekir.common.dto.debitcredit;

import com.ut.tekir.common.dto.MoneySetDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DebitCreditNoteDetailDTO(
    Long id,
    String serial,
    String code,
    LocalDate date,
    String action,
    Long contactId,
    String contactName,
    String info,
    String reference,
    List<DebitCreditNoteItemDTO> items,
    LocalDateTime createDate,
    String createUser
) {
    public record DebitCreditNoteItemDTO(
        Long id,
        MoneySetDTO amount,
        String info,
        String lineCode
    ) {}
}

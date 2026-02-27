package com.ut.tekir.common.dto.contact;

import com.ut.tekir.common.embeddable.MoneySet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactTransactionDTO {
    private Long id;
    private LocalDate date;
    private MoneySet debit;
    private MoneySet credit;
    private MoneySet bakiye;
    private String info;
    private String source;
    private String documentClass;
    private String code;
}

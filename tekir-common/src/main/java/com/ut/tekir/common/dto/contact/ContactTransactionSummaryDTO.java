package com.ut.tekir.common.dto.contact;

import com.ut.tekir.common.embeddable.MoneySet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactTransactionSummaryDTO {
    private MoneySet devredenBakiye;
    private MoneySet toplamBorc;
    private MoneySet toplamAlacak;
    private MoneySet kalanBakiye;
    private List<ContactTransactionDTO> transactions;
}

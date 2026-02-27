package com.ut.tekir.common.dto.payment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFilterModel {
    private String code;
    private String action;
    private Long contactId;
    private String contactCode;
    private String contactFullname;
    private Long accountId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}

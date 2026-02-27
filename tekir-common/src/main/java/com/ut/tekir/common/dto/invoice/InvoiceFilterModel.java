package com.ut.tekir.common.dto.invoice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceFilterModel {
    private String code;
    private String serial;
    private String reference;
    private String tradeAction;
    private Long contactId;
    private String contactCode;
    private String contactFullname;
    private Long warehouseId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}

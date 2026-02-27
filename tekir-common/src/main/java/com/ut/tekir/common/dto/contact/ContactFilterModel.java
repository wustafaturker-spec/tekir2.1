package com.ut.tekir.common.dto.contact;

import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.EntityType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFilterModel {
    private String code;
    private String name;
    private String taxNumber;
    private String ssn;
    private Long categoryId;
    private Long organizationId;
    private Boolean customerType;
    private Boolean providerType;
    private Boolean personnelType;
    private Boolean active;
    private EntityType entityType;
    private AccountStatus accountStatus;
    private Boolean eInvoiceRegistered;
    private String sector;
    private String customerSegment;
}

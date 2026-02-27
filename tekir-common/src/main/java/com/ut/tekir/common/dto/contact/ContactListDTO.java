package com.ut.tekir.common.dto.contact;

import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.EntityType;

public record ContactListDTO(
    Long id,
    String code,
    String name,
    String taxNumber,
    String ssn,
    String categoryName,
    EntityType entityType,
    Boolean customerType,
    Boolean providerType,
    Boolean personnelType,
    Boolean active,
    AccountStatus accountStatus,
    Boolean eInvoiceRegistered,
    String sector,
    String customerSegment
) {}

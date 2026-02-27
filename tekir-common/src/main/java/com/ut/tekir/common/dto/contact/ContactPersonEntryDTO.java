package com.ut.tekir.common.dto.contact;

public record ContactPersonEntryDTO(
    Long id,
    Long contactId,
    String firstName,
    String lastName,
    String jobTitle,
    String department,
    String email,
    String phone,
    String mobile,
    Boolean isDefault,
    String note
) {}

package com.ut.tekir.common.dto.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactPersonEntrySaveRequest(
    Long id,
    @NotNull Long contactId,
    @Size(max = 100) String firstName,
    @Size(max = 100) String lastName,
    @Size(max = 100) String jobTitle,
    @Size(max = 100) String department,
    @Email @Size(max = 255) String email,
    @Size(max = 30) String phone,
    @Size(max = 30) String mobile,
    Boolean isDefault,
    @Size(max = 500) String note
) {}

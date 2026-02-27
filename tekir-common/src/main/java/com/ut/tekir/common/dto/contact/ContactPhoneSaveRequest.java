package com.ut.tekir.common.dto.contact;

/**
 * Contact phone save request.
 */
public record ContactPhoneSaveRequest(
    Long id,
    String info,
    String countryCode,
    String areaCode,
    String phoneNumber,
    String extension,
    Boolean activePhone,
    Boolean defaultPhone,
    Boolean homePhone,
    Boolean workPhone,
    Boolean gsmPhone,
    Boolean faxPhone
) {}

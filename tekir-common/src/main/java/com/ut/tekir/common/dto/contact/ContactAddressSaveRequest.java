package com.ut.tekir.common.dto.contact;

import jakarta.validation.constraints.Size;

public record ContactAddressSaveRequest(
    Long id,
    @Size(max = 250) String street,
    @Size(max = 40)  String city,
    @Size(max = 40)  String province,
    @Size(max = 40)  String country,
    @Size(max = 10)  String zip,
    // Adres tipi (biri true olabilir)
    Boolean invoiceAddress,
    Boolean shippingAddress,
    Boolean homeAddress,
    Boolean workAddress,
    Boolean otherAddress,
    // Meta
    Boolean defaultAddress,
    Boolean activeAddress,
    @Size(max = 30) String recipientName,
    @Size(max = 30) String recipientSurname,
    String info
) {}

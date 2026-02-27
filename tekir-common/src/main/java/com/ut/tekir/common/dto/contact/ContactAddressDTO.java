package com.ut.tekir.common.dto.contact;

/**
 * Contact address DTO — düz metin alanları, legacy FK alanları yok.
 */
public record ContactAddressDTO(
    Long id,
    String info,
    // Gömülü adres alanları (Address embeddable)
    String street,
    String city,
    String province,
    String country,
    String zip,
    // Durum
    Boolean activeAddress,
    Boolean defaultAddress,
    // Adres tipi flag'leri
    Boolean invoiceAddress,
    Boolean shippingAddress,
    Boolean homeAddress,
    Boolean workAddress,
    Boolean otherAddress,
    // Alıcı
    String recipientName,
    String recipientSurname
) {}

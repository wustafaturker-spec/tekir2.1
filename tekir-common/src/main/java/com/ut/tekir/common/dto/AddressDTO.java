package com.ut.tekir.common.dto;

/**
 * Embedded Address DTO.
 */
public record AddressDTO(
    String address,
    String zip,
    String cityName,
    String provinceName,
    String countryName
) {}

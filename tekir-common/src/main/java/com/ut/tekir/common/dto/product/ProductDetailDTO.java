package com.ut.tekir.common.dto.product;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ut.tekir.common.enums.ProductType;

/**
 * Product detail DTO.
 */
public record ProductDetailDTO(
    Long id,
    String code,
    String name,
    String info,
    ProductType productType,
    Long categoryId,
    String categoryCode,
    String unit,
    String barcode1,
    String barcode2,
    String barcode3,
    String image,
    String groupCode,
    Integer weight,
    Long buyTaxId,
    String buyTaxName,
    Long sellTaxId,
    String sellTaxName,
    Boolean active,
    LocalDate openDate,
    LocalDateTime createDate,
    String createUser
) {}

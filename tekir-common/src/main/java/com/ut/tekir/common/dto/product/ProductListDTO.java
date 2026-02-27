package com.ut.tekir.common.dto.product;

import com.ut.tekir.common.enums.ProductType;

/**
 * Product list DTO — lightweight projection for browse views.
 */
public record ProductListDTO(
    Long id,
    String code,
    String name,
    ProductType productType,
    String categoryCode,
    String unit,
    String barcode1,
    Boolean active
) {}

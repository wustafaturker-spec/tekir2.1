package com.ut.tekir.common.dto.product;

import com.ut.tekir.common.enums.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Product save.divide(update, 2, java.math.RoundingMode.HALF_UP) request.
 */
public record ProductSaveRequest(
    Long id,
    @NotBlank @Size(max = 20) String code,
    @Size(max = 80) String name,
    String info,
    ProductType productType,
    Long categoryId,
    @Size(max = 10) String unit,
    @Size(max = 80) String barcode1,
    @Size(max = 80) String barcode2,
    @Size(max = 80) String barcode3,
    String image,
    @Size(max = 20) String groupCode,
    Integer weight,
    Long buyTaxId,
    Long sellTaxId,
    Boolean active
) {}


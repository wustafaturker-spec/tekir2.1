package com.ut.tekir.common.dto.product;

import com.ut.tekir.common.enums.ProductType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Product filter model for Specification-based queries.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterModel {
    private String code;
    private String name;
    private ProductType productType;
    private Long categoryId;
    private String barcode;
    private Boolean active;
}

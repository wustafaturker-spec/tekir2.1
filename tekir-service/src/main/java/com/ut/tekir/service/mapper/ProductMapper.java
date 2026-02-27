package com.ut.tekir.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ut.tekir.common.dto.product.ProductDetailDTO;
import com.ut.tekir.common.dto.product.ProductListDTO;
import com.ut.tekir.common.dto.product.ProductSaveRequest;
import com.ut.tekir.common.entity.Product;

/**
 * MapStruct mapper for Product entity ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryCode", source = "category.code")
    ProductListDTO toListDTO(Product entity);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryCode", source = "category.code")
    @Mapping(target = "buyTaxId", source = "buyTax.id")
    @Mapping(target = "buyTaxName", source = "buyTax.name")
    @Mapping(target = "sellTaxId", source = "sellTax.id")
    @Mapping(target = "sellTaxName", source = "sellTax.name")
    ProductDetailDTO toDetailDTO(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "buyTax", ignore = true)
    @Mapping(target = "sellTax", ignore = true)
    @Mapping(target = "openDate", ignore = true)
    @Mapping(target = "system", ignore = true)
    @Mapping(target = "expenseTypeId", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    void updateEntity(@MappingTarget Product entity, ProductSaveRequest request);
}

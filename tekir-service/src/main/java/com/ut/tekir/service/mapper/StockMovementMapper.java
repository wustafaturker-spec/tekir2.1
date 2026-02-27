package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.stockmovement.StockMovementListDTO;
import com.ut.tekir.common.entity.ProductTransfer;
import com.ut.tekir.common.entity.ProductVirement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {

    @Mapping(target = "documentType", constant = "ProductTransfer")
    @Mapping(target = "fromWarehouseName", source = "fromWarehouse.name")
    @Mapping(target = "toWarehouseName", source = "toWarehouse.name")
    StockMovementListDTO transferToListDTO(ProductTransfer entity);

    @Mapping(target = "documentType", constant = "ProductVirement")
    @Mapping(target = "fromWarehouseName", source = "warehouse.name")
    @Mapping(target = "toWarehouseName", ignore = true)
    StockMovementListDTO virementToListDTO(ProductVirement entity);
}

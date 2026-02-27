package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.stockmovement.StockMovementListDTO;
import com.ut.tekir.common.entity.ProductTransfer;
import com.ut.tekir.common.entity.ProductVirement;
import com.ut.tekir.common.entity.Warehouse;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class StockMovementMapperImpl implements StockMovementMapper {

    @Override
    public StockMovementListDTO transferToListDTO(ProductTransfer entity) {
        if ( entity == null ) {
            return null;
        }

        String fromWarehouseName = null;
        String toWarehouseName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        fromWarehouseName = entityFromWarehouseName( entity );
        toWarehouseName = entityToWarehouseName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        String documentType = "ProductTransfer";

        StockMovementListDTO stockMovementListDTO = new StockMovementListDTO( id, serial, code, date, documentType, fromWarehouseName, toWarehouseName, info );

        return stockMovementListDTO;
    }

    @Override
    public StockMovementListDTO virementToListDTO(ProductVirement entity) {
        if ( entity == null ) {
            return null;
        }

        String fromWarehouseName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        fromWarehouseName = entityWarehouseName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        String documentType = "ProductVirement";
        String toWarehouseName = null;

        StockMovementListDTO stockMovementListDTO = new StockMovementListDTO( id, serial, code, date, documentType, fromWarehouseName, toWarehouseName, info );

        return stockMovementListDTO;
    }

    private String entityFromWarehouseName(ProductTransfer productTransfer) {
        if ( productTransfer == null ) {
            return null;
        }
        Warehouse fromWarehouse = productTransfer.getFromWarehouse();
        if ( fromWarehouse == null ) {
            return null;
        }
        String name = fromWarehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityToWarehouseName(ProductTransfer productTransfer) {
        if ( productTransfer == null ) {
            return null;
        }
        Warehouse toWarehouse = productTransfer.getToWarehouse();
        if ( toWarehouse == null ) {
            return null;
        }
        String name = toWarehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityWarehouseName(ProductVirement productVirement) {
        if ( productVirement == null ) {
            return null;
        }
        Warehouse warehouse = productVirement.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        String name = warehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

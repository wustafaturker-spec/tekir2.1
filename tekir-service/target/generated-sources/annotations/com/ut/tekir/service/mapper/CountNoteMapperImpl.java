package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.countnote.CountNoteDetailDTO;
import com.ut.tekir.common.dto.countnote.CountNoteItemDTO;
import com.ut.tekir.common.dto.countnote.CountNoteListDTO;
import com.ut.tekir.common.entity.CountNote;
import com.ut.tekir.common.entity.CountNoteItem;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.Warehouse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CountNoteMapperImpl implements CountNoteMapper {

    @Override
    public CountNoteListDTO toListDTO(CountNote entity) {
        if ( entity == null ) {
            return null;
        }

        String warehouseName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        Boolean approved = null;
        String info = null;

        warehouseName = entityWarehouseName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        approved = entity.getApproved();
        info = entity.getInfo();

        CountNoteListDTO countNoteListDTO = new CountNoteListDTO( id, serial, code, date, warehouseName, approved, info );

        return countNoteListDTO;
    }

    @Override
    public CountNoteDetailDTO toDetailDTO(CountNote entity) {
        if ( entity == null ) {
            return null;
        }

        Long warehouseId = null;
        String warehouseName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        Boolean approved = null;
        String info = null;
        String reference = null;
        List<CountNoteItemDTO> items = null;
        LocalDateTime createDate = null;
        String createUser = null;

        warehouseId = entityWarehouseId( entity );
        warehouseName = entityWarehouseName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        approved = entity.getApproved();
        info = entity.getInfo();
        reference = entity.getReference();
        items = toItemDTOList( entity.getItems() );
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        CountNoteDetailDTO countNoteDetailDTO = new CountNoteDetailDTO( id, serial, code, date, warehouseId, warehouseName, approved, info, reference, items, createDate, createUser );

        return countNoteDetailDTO;
    }

    @Override
    public CountNoteItemDTO toItemDTO(CountNoteItem item) {
        if ( item == null ) {
            return null;
        }

        Long productId = null;
        String productName = null;
        Long id = null;
        Integer countQuantity = null;
        Integer existingQuantity = null;
        String lineCode = null;
        String info = null;

        productId = itemProductId( item );
        productName = itemProductName( item );
        id = item.getId();
        countQuantity = item.getCountQuantity();
        existingQuantity = item.getExistingQuantity();
        lineCode = item.getLineCode();
        info = item.getInfo();

        CountNoteItemDTO countNoteItemDTO = new CountNoteItemDTO( id, productId, productName, countQuantity, existingQuantity, lineCode, info );

        return countNoteItemDTO;
    }

    @Override
    public List<CountNoteItemDTO> toItemDTOList(List<CountNoteItem> items) {
        if ( items == null ) {
            return null;
        }

        List<CountNoteItemDTO> list = new ArrayList<CountNoteItemDTO>( items.size() );
        for ( CountNoteItem countNoteItem : items ) {
            list.add( toItemDTO( countNoteItem ) );
        }

        return list;
    }

    private String entityWarehouseName(CountNote countNote) {
        if ( countNote == null ) {
            return null;
        }
        Warehouse warehouse = countNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        String name = warehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityWarehouseId(CountNote countNote) {
        if ( countNote == null ) {
            return null;
        }
        Warehouse warehouse = countNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        Long id = warehouse.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long itemProductId(CountNoteItem countNoteItem) {
        if ( countNoteItem == null ) {
            return null;
        }
        Product product = countNoteItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String itemProductName(CountNoteItem countNoteItem) {
        if ( countNoteItem == null ) {
            return null;
        }
        Product product = countNoteItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

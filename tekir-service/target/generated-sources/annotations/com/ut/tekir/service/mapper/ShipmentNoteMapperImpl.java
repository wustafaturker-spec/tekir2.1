package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.shipment.ShipmentItemDTO;
import com.ut.tekir.common.dto.shipment.ShipmentNoteDetailDTO;
import com.ut.tekir.common.dto.shipment.ShipmentNoteListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.embeddable.TaxEmbeddable;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.ShipmentItem;
import com.ut.tekir.common.entity.ShipmentNote;
import com.ut.tekir.common.entity.Warehouse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:50+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ShipmentNoteMapperImpl implements ShipmentNoteMapper {

    @Override
    public ShipmentNoteListDTO toListDTO(ShipmentNote entity) {
        if ( entity == null ) {
            return null;
        }

        String contactCode = null;
        String contactFullname = null;
        String warehouseName = null;
        Long id = null;
        String code = null;
        String serial = null;
        String reference = null;
        LocalDate date = null;
        String info = null;

        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        warehouseName = entityWarehouseName( entity );
        id = entity.getId();
        code = entity.getCode();
        serial = entity.getSerial();
        reference = entity.getReference();
        date = entity.getDate();
        info = entity.getInfo();

        String tradeAction = entity.getAction() != null ? entity.getAction().name() : null;

        ShipmentNoteListDTO shipmentNoteListDTO = new ShipmentNoteListDTO( id, code, serial, reference, date, tradeAction, contactCode, contactFullname, warehouseName, info );

        return shipmentNoteListDTO;
    }

    @Override
    public ShipmentNoteDetailDTO toDetailDTO(ShipmentNote entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        String contactCode = null;
        String contactFullname = null;
        Long warehouseId = null;
        String warehouseName = null;
        Long id = null;
        String code = null;
        String serial = null;
        String reference = null;
        LocalDate date = null;
        List<ShipmentItemDTO> items = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;

        contactId = entityContactId( entity );
        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        warehouseId = entityWarehouseId( entity );
        warehouseName = entityWarehouseName( entity );
        id = entity.getId();
        code = entity.getCode();
        serial = entity.getSerial();
        reference = entity.getReference();
        date = entity.getDate();
        items = toItemDTOList( entity.getItems() );
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();

        String tradeAction = entity.getAction() != null ? entity.getAction().name() : null;

        ShipmentNoteDetailDTO shipmentNoteDetailDTO = new ShipmentNoteDetailDTO( id, code, serial, reference, date, tradeAction, contactId, contactCode, contactFullname, warehouseId, warehouseName, items, info, createDate, createUser, updateDate );

        return shipmentNoteDetailDTO;
    }

    @Override
    public ShipmentItemDTO toItemDTO(ShipmentItem entity) {
        if ( entity == null ) {
            return null;
        }

        Long productId = null;
        String productCode = null;
        String productName = null;
        BigDecimal quantity = null;
        String unitName = null;
        BigDecimal unitPrice = null;
        String currency = null;
        BigDecimal amount = null;
        BigDecimal taxRate = null;
        Long id = null;
        String info = null;

        productId = entityProductId( entity );
        productCode = entityProductCode( entity );
        productName = entityProductName( entity );
        quantity = entityQuantityValue( entity );
        unitName = entityQuantityUnit( entity );
        unitPrice = entityUnitPriceValue( entity );
        currency = entityUnitPriceCurrency( entity );
        amount = entityAmountValue( entity );
        taxRate = entityTaxRate( entity );
        id = entity.getId();
        info = entity.getInfo();

        ShipmentItemDTO shipmentItemDTO = new ShipmentItemDTO( id, productId, productCode, productName, quantity, unitName, unitPrice, currency, amount, taxRate, info );

        return shipmentItemDTO;
    }

    @Override
    public List<ShipmentItemDTO> toItemDTOList(List<ShipmentItem> items) {
        if ( items == null ) {
            return null;
        }

        List<ShipmentItemDTO> list = new ArrayList<ShipmentItemDTO>( items.size() );
        for ( ShipmentItem shipmentItem : items ) {
            list.add( toItemDTO( shipmentItem ) );
        }

        return list;
    }

    private String entityContactCode(ShipmentNote shipmentNote) {
        if ( shipmentNote == null ) {
            return null;
        }
        Contact contact = shipmentNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String code = contact.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityContactFullname(ShipmentNote shipmentNote) {
        if ( shipmentNote == null ) {
            return null;
        }
        Contact contact = shipmentNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String fullname = contact.getFullname();
        if ( fullname == null ) {
            return null;
        }
        return fullname;
    }

    private String entityWarehouseName(ShipmentNote shipmentNote) {
        if ( shipmentNote == null ) {
            return null;
        }
        Warehouse warehouse = shipmentNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        String name = warehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityContactId(ShipmentNote shipmentNote) {
        if ( shipmentNote == null ) {
            return null;
        }
        Contact contact = shipmentNote.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityWarehouseId(ShipmentNote shipmentNote) {
        if ( shipmentNote == null ) {
            return null;
        }
        Warehouse warehouse = shipmentNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        Long id = warehouse.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityProductId(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        Product product = shipmentItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProductCode(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        Product product = shipmentItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String code = product.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityProductName(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        Product product = shipmentItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal entityQuantityValue(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        Quantity quantity = shipmentItem.getQuantity();
        if ( quantity == null ) {
            return null;
        }
        BigDecimal value = quantity.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityQuantityUnit(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        Quantity quantity = shipmentItem.getQuantity();
        if ( quantity == null ) {
            return null;
        }
        String unit = quantity.getUnit();
        if ( unit == null ) {
            return null;
        }
        return unit;
    }

    private BigDecimal entityUnitPriceValue(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        MoneySet unitPrice = shipmentItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        BigDecimal value = unitPrice.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityUnitPriceCurrency(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        MoneySet unitPrice = shipmentItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        String currency = unitPrice.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private BigDecimal entityAmountValue(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        MoneySet amount = shipmentItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private BigDecimal entityTaxRate(ShipmentItem shipmentItem) {
        if ( shipmentItem == null ) {
            return null;
        }
        TaxEmbeddable tax = shipmentItem.getTax();
        if ( tax == null ) {
            return null;
        }
        BigDecimal rate = tax.getRate();
        if ( rate == null ) {
            return null;
        }
        return rate;
    }
}

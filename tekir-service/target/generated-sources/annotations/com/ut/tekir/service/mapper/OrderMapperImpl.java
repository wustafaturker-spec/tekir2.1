package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.order.OrderDetailDTO;
import com.ut.tekir.common.dto.order.OrderItemDTO;
import com.ut.tekir.common.dto.order.OrderListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.embeddable.TaxEmbeddable;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.OrderItem;
import com.ut.tekir.common.entity.OrderNote;
import com.ut.tekir.common.entity.Product;
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
    date = "2026-02-26T21:57:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderListDTO toListDTO(OrderNote entity) {
        if ( entity == null ) {
            return null;
        }

        String contactCode = null;
        String contactFullname = null;
        Long id = null;
        String code = null;
        String serial = null;
        String reference = null;
        LocalDate date = null;
        String info = null;

        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        id = entity.getId();
        code = entity.getCode();
        serial = entity.getSerial();
        reference = entity.getReference();
        date = entity.getDate();
        info = entity.getInfo();

        String tradeAction = entity.getAction() != null ? entity.getAction().name() : null;
        String orderStatus = null;
        BigDecimal grandTotalValue = null;
        String grandTotalCurrency = null;

        OrderListDTO orderListDTO = new OrderListDTO( id, code, serial, reference, date, tradeAction, orderStatus, contactCode, contactFullname, grandTotalValue, grandTotalCurrency, info );

        return orderListDTO;
    }

    @Override
    public OrderDetailDTO toDetailDTO(OrderNote entity) {
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
        List<OrderItemDTO> items = null;
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
        String orderStatus = null;
        MoneySetDTO grandTotal = null;

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO( id, code, serial, reference, date, tradeAction, orderStatus, contactId, contactCode, contactFullname, warehouseId, warehouseName, grandTotal, items, info, createDate, createUser, updateDate );

        return orderDetailDTO;
    }

    @Override
    public OrderItemDTO toItemDTO(OrderItem entity) {
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
        BigDecimal lineTotal = null;
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
        lineTotal = entityAmountLocalAmount( entity );
        id = entity.getId();
        info = entity.getInfo();

        BigDecimal discountRate = null;
        Integer lineNo = null;

        OrderItemDTO orderItemDTO = new OrderItemDTO( id, lineNo, productId, productCode, productName, quantity, unitName, unitPrice, currency, amount, taxRate, discountRate, lineTotal, info );

        return orderItemDTO;
    }

    @Override
    public List<OrderItemDTO> toItemDTOList(List<OrderItem> items) {
        if ( items == null ) {
            return null;
        }

        List<OrderItemDTO> list = new ArrayList<OrderItemDTO>( items.size() );
        for ( OrderItem orderItem : items ) {
            list.add( toItemDTO( orderItem ) );
        }

        return list;
    }

    private String entityContactCode(OrderNote orderNote) {
        if ( orderNote == null ) {
            return null;
        }
        Contact contact = orderNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String code = contact.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityContactFullname(OrderNote orderNote) {
        if ( orderNote == null ) {
            return null;
        }
        Contact contact = orderNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String fullname = contact.getFullname();
        if ( fullname == null ) {
            return null;
        }
        return fullname;
    }

    private Long entityContactId(OrderNote orderNote) {
        if ( orderNote == null ) {
            return null;
        }
        Contact contact = orderNote.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityWarehouseId(OrderNote orderNote) {
        if ( orderNote == null ) {
            return null;
        }
        Warehouse warehouse = orderNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        Long id = warehouse.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityWarehouseName(OrderNote orderNote) {
        if ( orderNote == null ) {
            return null;
        }
        Warehouse warehouse = orderNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        String name = warehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityProductId(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProductCode(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String code = product.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityProductName(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal entityQuantityValue(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Quantity quantity = orderItem.getQuantity();
        if ( quantity == null ) {
            return null;
        }
        BigDecimal value = quantity.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityQuantityUnit(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Quantity quantity = orderItem.getQuantity();
        if ( quantity == null ) {
            return null;
        }
        String unit = quantity.getUnit();
        if ( unit == null ) {
            return null;
        }
        return unit;
    }

    private BigDecimal entityUnitPriceValue(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        MoneySet unitPrice = orderItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        BigDecimal value = unitPrice.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityUnitPriceCurrency(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        MoneySet unitPrice = orderItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        String currency = unitPrice.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private BigDecimal entityAmountValue(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        MoneySet amount = orderItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private BigDecimal entityTaxRate(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        TaxEmbeddable tax = orderItem.getTax();
        if ( tax == null ) {
            return null;
        }
        BigDecimal rate = tax.getRate();
        if ( rate == null ) {
            return null;
        }
        return rate;
    }

    private BigDecimal entityAmountLocalAmount(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        MoneySet amount = orderItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal localAmount = amount.getLocalAmount();
        if ( localAmount == null ) {
            return null;
        }
        return localAmount;
    }
}

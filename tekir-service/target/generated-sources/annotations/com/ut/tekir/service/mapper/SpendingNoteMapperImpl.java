package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.spendingnote.SpendingNoteDetailDTO;
import com.ut.tekir.common.dto.spendingnote.SpendingNoteItemDTO;
import com.ut.tekir.common.dto.spendingnote.SpendingNoteListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.Employee;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.SpendingNote;
import com.ut.tekir.common.entity.SpendingNoteItem;
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
    date = "2026-02-26T21:57:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class SpendingNoteMapperImpl implements SpendingNoteMapper {

    @Override
    public SpendingNoteListDTO toListDTO(SpendingNote entity) {
        if ( entity == null ) {
            return null;
        }

        String warehouseName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        warehouseName = entityWarehouseName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        String employeeName = entity.getEmployee() != null ? entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName() : null;

        SpendingNoteListDTO spendingNoteListDTO = new SpendingNoteListDTO( id, serial, code, date, employeeName, warehouseName, info );

        return spendingNoteListDTO;
    }

    @Override
    public SpendingNoteDetailDTO toDetailDTO(SpendingNote entity) {
        if ( entity == null ) {
            return null;
        }

        Long employeeId = null;
        Long warehouseId = null;
        String warehouseName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;
        String reference = null;
        List<SpendingNoteItemDTO> items = null;
        LocalDateTime createDate = null;
        String createUser = null;

        employeeId = entityEmployeeId( entity );
        warehouseId = entityWarehouseId( entity );
        warehouseName = entityWarehouseName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();
        reference = entity.getReference();
        items = toItemDTOList( entity.getItems() );
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        String employeeName = entity.getEmployee() != null ? entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName() : null;
        String action = entity.getAction() != null ? entity.getAction().name() : null;

        SpendingNoteDetailDTO spendingNoteDetailDTO = new SpendingNoteDetailDTO( id, serial, code, date, employeeId, employeeName, warehouseId, warehouseName, action, info, reference, items, createDate, createUser );

        return spendingNoteDetailDTO;
    }

    @Override
    public SpendingNoteItemDTO toItemDTO(SpendingNoteItem item) {
        if ( item == null ) {
            return null;
        }

        Long productId = null;
        String productName = null;
        Long id = null;
        BigDecimal quantity = null;
        MoneySetDTO unitPrice = null;
        MoneySetDTO amount = null;
        String lineCode = null;
        String info = null;

        productId = itemProductId( item );
        productName = itemProductName( item );
        id = item.getId();
        quantity = item.getQuantity();
        unitPrice = moneySetToMoneySetDTO( item.getUnitPrice() );
        amount = moneySetToMoneySetDTO( item.getAmount() );
        lineCode = item.getLineCode();
        info = item.getInfo();

        SpendingNoteItemDTO spendingNoteItemDTO = new SpendingNoteItemDTO( id, productId, productName, quantity, unitPrice, amount, lineCode, info );

        return spendingNoteItemDTO;
    }

    @Override
    public List<SpendingNoteItemDTO> toItemDTOList(List<SpendingNoteItem> items) {
        if ( items == null ) {
            return null;
        }

        List<SpendingNoteItemDTO> list = new ArrayList<SpendingNoteItemDTO>( items.size() );
        for ( SpendingNoteItem spendingNoteItem : items ) {
            list.add( toItemDTO( spendingNoteItem ) );
        }

        return list;
    }

    private String entityWarehouseName(SpendingNote spendingNote) {
        if ( spendingNote == null ) {
            return null;
        }
        Warehouse warehouse = spendingNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        String name = warehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityEmployeeId(SpendingNote spendingNote) {
        if ( spendingNote == null ) {
            return null;
        }
        Employee employee = spendingNote.getEmployee();
        if ( employee == null ) {
            return null;
        }
        Long id = employee.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityWarehouseId(SpendingNote spendingNote) {
        if ( spendingNote == null ) {
            return null;
        }
        Warehouse warehouse = spendingNote.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        Long id = warehouse.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long itemProductId(SpendingNoteItem spendingNoteItem) {
        if ( spendingNoteItem == null ) {
            return null;
        }
        Product product = spendingNoteItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String itemProductName(SpendingNoteItem spendingNoteItem) {
        if ( spendingNoteItem == null ) {
            return null;
        }
        Product product = spendingNoteItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected MoneySetDTO moneySetToMoneySetDTO(MoneySet moneySet) {
        if ( moneySet == null ) {
            return null;
        }

        String currency = null;
        BigDecimal value = null;
        BigDecimal localAmount = null;

        currency = moneySet.getCurrency();
        value = moneySet.getValue();
        localAmount = moneySet.getLocalAmount();

        MoneySetDTO moneySetDTO = new MoneySetDTO( currency, value, localAmount );

        return moneySetDTO;
    }
}

package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.expense.ExpenseItemDTO;
import com.ut.tekir.common.dto.expense.ExpenseNoteDetailDTO;
import com.ut.tekir.common.dto.expense.ExpenseNoteListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.TaxEmbeddable;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ExpenseItem;
import com.ut.tekir.common.entity.ExpenseNote;
import com.ut.tekir.common.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:46+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ExpenseNoteMapperImpl implements ExpenseNoteMapper {

    @Override
    public ExpenseNoteListDTO toListDTO(ExpenseNote entity) {
        if ( entity == null ) {
            return null;
        }

        String contactCode = null;
        String contactFullname = null;
        String accountName = null;
        Long id = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        accountName = entityAccountName( entity );
        id = entity.getId();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        ExpenseNoteListDTO expenseNoteListDTO = new ExpenseNoteListDTO( id, code, date, contactCode, contactFullname, accountName, info );

        return expenseNoteListDTO;
    }

    @Override
    public ExpenseNoteDetailDTO toDetailDTO(ExpenseNote entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        String contactCode = null;
        String contactFullname = null;
        Long accountId = null;
        String accountName = null;
        Long id = null;
        String code = null;
        LocalDate date = null;
        List<ExpenseItemDTO> items = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;

        contactId = entityContactId( entity );
        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        accountId = entityAccountId( entity );
        accountName = entityAccountName( entity );
        id = entity.getId();
        code = entity.getCode();
        date = entity.getDate();
        items = toItemDTOList( entity.getItems() );
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();

        ExpenseNoteDetailDTO expenseNoteDetailDTO = new ExpenseNoteDetailDTO( id, code, date, contactId, contactCode, contactFullname, accountId, accountName, items, info, createDate, createUser, updateDate );

        return expenseNoteDetailDTO;
    }

    @Override
    public ExpenseItemDTO toItemDTO(ExpenseItem entity) {
        if ( entity == null ) {
            return null;
        }

        Long serviceId = null;
        String serviceCode = null;
        String serviceName = null;
        BigDecimal amount = null;
        String currency = null;
        BigDecimal localAmount = null;
        BigDecimal taxRate = null;
        Long id = null;
        String info = null;

        serviceId = entityServiceId( entity );
        serviceCode = entityServiceCode( entity );
        serviceName = entityServiceName( entity );
        amount = entityAmountValue( entity );
        currency = entityAmountCurrency( entity );
        localAmount = entityAmountLocalAmount( entity );
        taxRate = entityTaxRate( entity );
        id = entity.getId();
        info = entity.getInfo();

        ExpenseItemDTO expenseItemDTO = new ExpenseItemDTO( id, serviceId, serviceCode, serviceName, amount, currency, localAmount, taxRate, info );

        return expenseItemDTO;
    }

    @Override
    public List<ExpenseItemDTO> toItemDTOList(List<ExpenseItem> items) {
        if ( items == null ) {
            return null;
        }

        List<ExpenseItemDTO> list = new ArrayList<ExpenseItemDTO>( items.size() );
        for ( ExpenseItem expenseItem : items ) {
            list.add( toItemDTO( expenseItem ) );
        }

        return list;
    }

    private String entityContactCode(ExpenseNote expenseNote) {
        if ( expenseNote == null ) {
            return null;
        }
        Contact contact = expenseNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String code = contact.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityContactFullname(ExpenseNote expenseNote) {
        if ( expenseNote == null ) {
            return null;
        }
        Contact contact = expenseNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String fullname = contact.getFullname();
        if ( fullname == null ) {
            return null;
        }
        return fullname;
    }

    private String entityAccountName(ExpenseNote expenseNote) {
        if ( expenseNote == null ) {
            return null;
        }
        Account account = expenseNote.getAccount();
        if ( account == null ) {
            return null;
        }
        String name = account.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityContactId(ExpenseNote expenseNote) {
        if ( expenseNote == null ) {
            return null;
        }
        Contact contact = expenseNote.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityAccountId(ExpenseNote expenseNote) {
        if ( expenseNote == null ) {
            return null;
        }
        Account account = expenseNote.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityServiceId(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        Product service = expenseItem.getService();
        if ( service == null ) {
            return null;
        }
        Long id = service.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityServiceCode(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        Product service = expenseItem.getService();
        if ( service == null ) {
            return null;
        }
        String code = service.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityServiceName(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        Product service = expenseItem.getService();
        if ( service == null ) {
            return null;
        }
        String name = service.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal entityAmountValue(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        MoneySet amount = expenseItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityAmountCurrency(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        MoneySet amount = expenseItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        String currency = amount.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private BigDecimal entityAmountLocalAmount(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        MoneySet amount = expenseItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal localAmount = amount.getLocalAmount();
        if ( localAmount == null ) {
            return null;
        }
        return localAmount;
    }

    private BigDecimal entityTaxRate(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        TaxEmbeddable tax = expenseItem.getTax();
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

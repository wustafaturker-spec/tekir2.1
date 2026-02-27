package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.promissory.PromissoryNoteDetailDTO;
import com.ut.tekir.common.dto.promissory.PromissoryNoteListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.PromissoryNote;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PromissoryNoteMapperImpl implements PromissoryNoteMapper {

    @Override
    public PromissoryNoteListDTO toListDTO(PromissoryNote entity) {
        if ( entity == null ) {
            return null;
        }

        BigDecimal amount = null;
        String currency = null;
        LocalDate dueDate = null;
        String contactFullname = null;
        String direction = null;
        String promissoryNumber = null;
        Long id = null;
        String code = null;
        String promissoryOwner = null;
        String info = null;

        amount = entityAmountValue( entity );
        currency = entityAmountCurrency( entity );
        dueDate = entity.getMaturityDate();
        contactFullname = entityContactName( entity );
        if ( entity.getDirection() != null ) {
            direction = entity.getDirection().name();
        }
        promissoryNumber = entity.getReference();
        id = entity.getId();
        code = entity.getCode();
        promissoryOwner = entity.getPromissoryOwner();
        info = entity.getInfo();

        String status = entity.getStatus() != null ? entity.getStatus().name() : null;

        PromissoryNoteListDTO promissoryNoteListDTO = new PromissoryNoteListDTO( id, code, promissoryOwner, status, amount, currency, dueDate, contactFullname, direction, promissoryNumber, info );

        return promissoryNoteListDTO;
    }

    @Override
    public PromissoryNoteDetailDTO toDetailDTO(PromissoryNote entity) {
        if ( entity == null ) {
            return null;
        }

        MoneySetDTO money = null;
        LocalDate dueDate = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;
        String promissoryNumber = null;
        Long contactId = null;
        String contactCode = null;
        String contactFullname = null;
        String direction = null;
        Long id = null;
        String code = null;
        String promissoryOwner = null;
        String paymentPlace = null;
        String info = null;

        money = moneySetToMoneySetDTO( entity.getAmount() );
        dueDate = entity.getMaturityDate();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();
        promissoryNumber = entity.getReference();
        contactId = entityContactId( entity );
        contactCode = entityContactCode( entity );
        contactFullname = entityContactName( entity );
        if ( entity.getDirection() != null ) {
            direction = entity.getDirection().name();
        }
        id = entity.getId();
        code = entity.getCode();
        promissoryOwner = entity.getPromissoryOwner();
        paymentPlace = entity.getPaymentPlace();
        info = entity.getInfo();

        String status = entity.getStatus() != null ? entity.getStatus().name() : null;

        PromissoryNoteDetailDTO promissoryNoteDetailDTO = new PromissoryNoteDetailDTO( id, code, status, promissoryOwner, money, dueDate, paymentPlace, info, createDate, createUser, updateDate, direction, promissoryNumber, contactId, contactCode, contactFullname );

        return promissoryNoteDetailDTO;
    }

    private BigDecimal entityAmountValue(PromissoryNote promissoryNote) {
        if ( promissoryNote == null ) {
            return null;
        }
        MoneySet amount = promissoryNote.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityAmountCurrency(PromissoryNote promissoryNote) {
        if ( promissoryNote == null ) {
            return null;
        }
        MoneySet amount = promissoryNote.getAmount();
        if ( amount == null ) {
            return null;
        }
        String currency = amount.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private String entityContactName(PromissoryNote promissoryNote) {
        if ( promissoryNote == null ) {
            return null;
        }
        Contact contact = promissoryNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String name = contact.getName();
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

    private Long entityContactId(PromissoryNote promissoryNote) {
        if ( promissoryNote == null ) {
            return null;
        }
        Contact contact = promissoryNote.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityContactCode(PromissoryNote promissoryNote) {
        if ( promissoryNote == null ) {
            return null;
        }
        Contact contact = promissoryNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String code = contact.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }
}

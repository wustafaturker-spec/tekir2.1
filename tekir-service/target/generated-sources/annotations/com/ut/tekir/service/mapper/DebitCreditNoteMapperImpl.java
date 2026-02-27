package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditNoteDetailDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditNoteListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.DebitCreditNote;
import com.ut.tekir.common.entity.DebitCreditNoteItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DebitCreditNoteMapperImpl implements DebitCreditNoteMapper {

    @Override
    public DebitCreditNoteListDTO toListDTO(DebitCreditNote entity) {
        if ( entity == null ) {
            return null;
        }

        String contactName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        contactName = entityContactName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        String action = entity.getAction() != null ? entity.getAction().name() : null;

        DebitCreditNoteListDTO debitCreditNoteListDTO = new DebitCreditNoteListDTO( id, serial, code, date, action, contactName, info );

        return debitCreditNoteListDTO;
    }

    @Override
    public DebitCreditNoteDetailDTO toDetailDTO(DebitCreditNote entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        String contactName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;
        String reference = null;
        List<DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO> items = null;
        LocalDateTime createDate = null;
        String createUser = null;

        contactId = entityContactId( entity );
        contactName = entityContactName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();
        reference = entity.getReference();
        items = toItemDTOList( entity.getItems() );
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        String action = entity.getAction() != null ? entity.getAction().name() : null;

        DebitCreditNoteDetailDTO debitCreditNoteDetailDTO = new DebitCreditNoteDetailDTO( id, serial, code, date, action, contactId, contactName, info, reference, items, createDate, createUser );

        return debitCreditNoteDetailDTO;
    }

    @Override
    public DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO toItemDTO(DebitCreditNoteItem item) {
        if ( item == null ) {
            return null;
        }

        Long id = null;
        MoneySetDTO amount = null;
        String info = null;
        String lineCode = null;

        id = item.getId();
        amount = moneySetToMoneySetDTO( item.getAmount() );
        info = item.getInfo();
        lineCode = item.getLineCode();

        DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO debitCreditNoteItemDTO = new DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO( id, amount, info, lineCode );

        return debitCreditNoteItemDTO;
    }

    @Override
    public List<DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO> toItemDTOList(List<DebitCreditNoteItem> items) {
        if ( items == null ) {
            return null;
        }

        List<DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO> list = new ArrayList<DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO>( items.size() );
        for ( DebitCreditNoteItem debitCreditNoteItem : items ) {
            list.add( toItemDTO( debitCreditNoteItem ) );
        }

        return list;
    }

    private String entityContactName(DebitCreditNote debitCreditNote) {
        if ( debitCreditNote == null ) {
            return null;
        }
        Contact contact = debitCreditNote.getContact();
        if ( contact == null ) {
            return null;
        }
        String name = contact.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityContactId(DebitCreditNote debitCreditNote) {
        if ( debitCreditNote == null ) {
            return null;
        }
        Contact contact = debitCreditNote.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
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

package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.accountcreditnote.AccountCreditNoteDetailDTO;
import com.ut.tekir.common.dto.accountcreditnote.AccountCreditNoteListDTO;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.AccountCreditNote;
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
public class AccountCreditNoteMapperImpl implements AccountCreditNoteMapper {

    @Override
    public AccountCreditNoteListDTO toListDTO(AccountCreditNote entity) {
        if ( entity == null ) {
            return null;
        }

        String accountName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        accountName = entityAccountName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        AccountCreditNoteListDTO accountCreditNoteListDTO = new AccountCreditNoteListDTO( id, serial, code, date, accountName, info );

        return accountCreditNoteListDTO;
    }

    @Override
    public AccountCreditNoteDetailDTO toDetailDTO(AccountCreditNote entity) {
        if ( entity == null ) {
            return null;
        }

        Long accountId = null;
        String accountName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;
        String reference = null;
        LocalDateTime createDate = null;
        String createUser = null;

        accountId = entityAccountId( entity );
        accountName = entityAccountName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();
        reference = entity.getReference();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        AccountCreditNoteDetailDTO accountCreditNoteDetailDTO = new AccountCreditNoteDetailDTO( id, serial, code, date, accountId, accountName, info, reference, createDate, createUser );

        return accountCreditNoteDetailDTO;
    }

    private String entityAccountName(AccountCreditNote accountCreditNote) {
        if ( accountCreditNote == null ) {
            return null;
        }
        Account account = accountCreditNote.getAccount();
        if ( account == null ) {
            return null;
        }
        String name = account.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityAccountId(AccountCreditNote accountCreditNote) {
        if ( accountCreditNote == null ) {
            return null;
        }
        Account account = accountCreditNote.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

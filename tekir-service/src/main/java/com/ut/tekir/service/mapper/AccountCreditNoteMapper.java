package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.accountcreditnote.AccountCreditNoteDetailDTO;
import com.ut.tekir.common.dto.accountcreditnote.AccountCreditNoteListDTO;
import com.ut.tekir.common.entity.AccountCreditNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountCreditNoteMapper {

    @Mapping(target = "accountName", source = "account.name")
    AccountCreditNoteListDTO toListDTO(AccountCreditNote entity);

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "accountName", source = "account.name")
    AccountCreditNoteDetailDTO toDetailDTO(AccountCreditNote entity);
}

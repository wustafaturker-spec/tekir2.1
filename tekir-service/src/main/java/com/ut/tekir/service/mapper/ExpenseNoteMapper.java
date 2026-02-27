package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.expense.ExpenseItemDTO;
import com.ut.tekir.common.dto.expense.ExpenseNoteDetailDTO;
import com.ut.tekir.common.dto.expense.ExpenseNoteListDTO;
import com.ut.tekir.common.entity.ExpenseItem;
import com.ut.tekir.common.entity.ExpenseNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for ExpenseNote ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface ExpenseNoteMapper {

    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "accountName", source = "account.name")
    ExpenseNoteListDTO toListDTO(ExpenseNote entity);

    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "accountName", source = "account.name")
    ExpenseNoteDetailDTO toDetailDTO(ExpenseNote entity);

    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "serviceCode", source = "service.code")
    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "currency", source = "amount.currency")
    @Mapping(target = "localAmount", source = "amount.localAmount")
    @Mapping(target = "taxRate", source = "tax.rate")
    ExpenseItemDTO toItemDTO(ExpenseItem entity);

    List<ExpenseItemDTO> toItemDTOList(List<ExpenseItem> items);
}

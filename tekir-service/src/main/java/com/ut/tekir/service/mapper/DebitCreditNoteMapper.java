package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.debitcredit.DebitCreditNoteDetailDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditNoteListDTO;
import com.ut.tekir.common.entity.DebitCreditNote;
import com.ut.tekir.common.entity.DebitCreditNoteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DebitCreditNoteMapper {

    @Mapping(target = "action", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactName", source = "contact.name")
    DebitCreditNoteListDTO toListDTO(DebitCreditNote entity);

    @Mapping(target = "action", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactName", source = "contact.name")
    DebitCreditNoteDetailDTO toDetailDTO(DebitCreditNote entity);

    DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO toItemDTO(DebitCreditNoteItem item);
    List<DebitCreditNoteDetailDTO.DebitCreditNoteItemDTO> toItemDTOList(List<DebitCreditNoteItem> items);
}

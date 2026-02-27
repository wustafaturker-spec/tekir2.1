package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.promissory.PromissoryNoteDetailDTO;
import com.ut.tekir.common.dto.promissory.PromissoryNoteListDTO;
import com.ut.tekir.common.entity.PromissoryNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromissoryNoteMapper {

    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "currency", source = "amount.currency")
    @Mapping(target = "dueDate", source = "maturityDate")
    @Mapping(target = "contactFullname", source = "contact.name")
    @Mapping(target = "direction", source = "direction")
    @Mapping(target = "promissoryNumber", source = "reference")
    PromissoryNoteListDTO toListDTO(PromissoryNote entity);

    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "money", source = "amount")
    @Mapping(target = "dueDate", source = "maturityDate")
    @Mapping(target = "createDate", source = "createDate")
    @Mapping(target = "createUser", source = "createUser")
    @Mapping(target = "updateDate", source = "updateDate")
    @Mapping(target = "promissoryNumber", source = "reference")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.name")
    @Mapping(target = "direction", source = "direction")
    PromissoryNoteDetailDTO toDetailDTO(PromissoryNote entity);
}

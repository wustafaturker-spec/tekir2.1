package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.spendingnote.SpendingNoteDetailDTO;
import com.ut.tekir.common.dto.spendingnote.SpendingNoteItemDTO;
import com.ut.tekir.common.dto.spendingnote.SpendingNoteListDTO;
import com.ut.tekir.common.entity.SpendingNote;
import com.ut.tekir.common.entity.SpendingNoteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpendingNoteMapper {

    @Mapping(target = "employeeName", expression = "java(entity.getEmployee() != null ? entity.getEmployee().getFirstName() + \" \" + entity.getEmployee().getLastName() : null)")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    SpendingNoteListDTO toListDTO(SpendingNote entity);

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", expression = "java(entity.getEmployee() != null ? entity.getEmployee().getFirstName() + \" \" + entity.getEmployee().getLastName() : null)")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    @Mapping(target = "action", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    SpendingNoteDetailDTO toDetailDTO(SpendingNote entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    SpendingNoteItemDTO toItemDTO(SpendingNoteItem item);

    List<SpendingNoteItemDTO> toItemDTOList(List<SpendingNoteItem> items);
}

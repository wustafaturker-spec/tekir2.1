package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.countnote.CountNoteDetailDTO;
import com.ut.tekir.common.dto.countnote.CountNoteItemDTO;
import com.ut.tekir.common.dto.countnote.CountNoteListDTO;
import com.ut.tekir.common.entity.CountNote;
import com.ut.tekir.common.entity.CountNoteItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountNoteMapper {

    @Mapping(target = "warehouseName", source = "warehouse.name")
    CountNoteListDTO toListDTO(CountNote entity);

    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    CountNoteDetailDTO toDetailDTO(CountNote entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    CountNoteItemDTO toItemDTO(CountNoteItem item);

    List<CountNoteItemDTO> toItemDTOList(List<CountNoteItem> items);
}

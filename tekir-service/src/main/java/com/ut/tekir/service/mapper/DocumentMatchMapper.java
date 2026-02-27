package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.documentmatch.DocumentMatchListDTO;
import com.ut.tekir.common.entity.DocumentMatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMatchMapper {

    @Mapping(target = "sourceDocumentType", expression = "java(entity.getSourceDocumentType() != null ? entity.getSourceDocumentType().name() : null)")
    @Mapping(target = "targetDocumentType", expression = "java(entity.getTargetDocumentType() != null ? entity.getTargetDocumentType().name() : null)")
    DocumentMatchListDTO toListDTO(DocumentMatch entity);
}

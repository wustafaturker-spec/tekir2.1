package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.interest.InterestDetailDTO;
import com.ut.tekir.common.dto.interest.InterestListDTO;
import com.ut.tekir.common.entity.Interest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterestMapper {

    InterestListDTO toListDTO(Interest entity);

    InterestDetailDTO toDetailDTO(Interest entity);
}

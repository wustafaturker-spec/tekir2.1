package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.interest.InterestDetailDTO;
import com.ut.tekir.common.dto.interest.InterestListDTO;
import com.ut.tekir.common.entity.Interest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:48+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class InterestMapperImpl implements InterestMapper {

    @Override
    public InterestListDTO toListDTO(Interest entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        BigDecimal rate = null;
        String info = null;

        id = entity.getId();
        code = entity.getCode();
        rate = entity.getRate();
        info = entity.getInfo();

        InterestListDTO interestListDTO = new InterestListDTO( id, code, rate, info );

        return interestListDTO;
    }

    @Override
    public InterestDetailDTO toDetailDTO(Interest entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        BigDecimal rate = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;

        id = entity.getId();
        code = entity.getCode();
        rate = entity.getRate();
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        InterestDetailDTO interestDetailDTO = new InterestDetailDTO( id, code, rate, info, createDate, createUser );

        return interestDetailDTO;
    }
}

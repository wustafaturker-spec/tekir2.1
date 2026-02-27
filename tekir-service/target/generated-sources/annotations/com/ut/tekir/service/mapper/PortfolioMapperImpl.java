package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.portfolio.PortfolioDetailDTO;
import com.ut.tekir.common.dto.portfolio.PortfolioListDTO;
import com.ut.tekir.common.entity.Portfolio;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PortfolioMapperImpl implements PortfolioMapper {

    @Override
    public PortfolioListDTO toListDTO(Portfolio entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;
        Boolean active = null;
        String info = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        active = entity.getActive();
        info = entity.getInfo();

        PortfolioListDTO portfolioListDTO = new PortfolioListDTO( id, code, name, active, info );

        return portfolioListDTO;
    }

    @Override
    public PortfolioDetailDTO toDetailDTO(Portfolio entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;
        Boolean active = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        active = entity.getActive();
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        PortfolioDetailDTO portfolioDetailDTO = new PortfolioDetailDTO( id, code, name, active, info, createDate, createUser );

        return portfolioDetailDTO;
    }
}

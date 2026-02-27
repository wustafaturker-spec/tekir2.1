package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.portfolio.PortfolioDetailDTO;
import com.ut.tekir.common.dto.portfolio.PortfolioListDTO;
import com.ut.tekir.common.entity.Portfolio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    PortfolioListDTO toListDTO(Portfolio entity);

    PortfolioDetailDTO toDetailDTO(Portfolio entity);
}

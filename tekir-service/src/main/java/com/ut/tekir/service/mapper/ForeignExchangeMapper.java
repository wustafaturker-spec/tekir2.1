package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.foreignexchange.ForeignExchangeDetailDTO;
import com.ut.tekir.common.dto.foreignexchange.ForeignExchangeListDTO;
import com.ut.tekir.common.entity.ForeignExchange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForeignExchangeMapper {

    ForeignExchangeListDTO toListDTO(ForeignExchange entity);

    @Mapping(target = "fromBankAccountId", source = "fromBankAccount.id")
    @Mapping(target = "fromBankAccountName", source = "fromBankAccount.name")
    @Mapping(target = "toBankAccountId", source = "toBankAccount.id")
    @Mapping(target = "toBankAccountName", source = "toBankAccount.name")
    ForeignExchangeDetailDTO toDetailDTO(ForeignExchange entity);
}

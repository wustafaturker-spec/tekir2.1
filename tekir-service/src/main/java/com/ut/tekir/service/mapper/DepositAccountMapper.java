package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.depositaccount.DepositAccountDetailDTO;
import com.ut.tekir.common.dto.depositaccount.DepositAccountListDTO;
import com.ut.tekir.common.entity.DepositAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepositAccountMapper {

    @Mapping(target = "bankAccountName", source = "bankAccount.name")
    DepositAccountListDTO toListDTO(DepositAccount entity);

    @Mapping(target = "bankAccountId", source = "bankAccount.id")
    @Mapping(target = "bankAccountName", source = "bankAccount.name")
    DepositAccountDetailDTO toDetailDTO(DepositAccount entity);
}

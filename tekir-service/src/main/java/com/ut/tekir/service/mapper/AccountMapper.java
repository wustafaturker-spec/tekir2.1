package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.account.AccountDetailDTO;
import com.ut.tekir.common.dto.account.AccountListDTO;
import com.ut.tekir.common.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for Account entity ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "accountType", expression = "java(entity.getAccountType() != null ? entity.getAccountType().name() : null)")
    @Mapping(target = "organizationName", source = "organization.name")
    AccountListDTO toListDTO(Account entity);

    @Mapping(target = "accountType", expression = "java(entity.getAccountType() != null ? entity.getAccountType().name() : null)")
    @Mapping(target = "organizationId", source = "organization.id")
    @Mapping(target = "organizationName", source = "organization.name")
    AccountDetailDTO toDetailDTO(Account entity);
}

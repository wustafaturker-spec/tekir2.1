package com.ut.tekir.accounting.mapper;

import com.ut.tekir.accounting.dto.AccountPlanDTO;
import com.ut.tekir.accounting.dto.AccountPlanSaveRequest;
import com.ut.tekir.accounting.entity.AccountPlan;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountPlanMapper {

    AccountPlanDTO toDto(AccountPlan entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "accountCount", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    AccountPlan toEntity(AccountPlanSaveRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "accountCount", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    void updateEntity(AccountPlanSaveRequest request, @MappingTarget AccountPlan entity);
}

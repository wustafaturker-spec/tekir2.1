package com.ut.tekir.accounting.mapper;

import com.ut.tekir.accounting.dto.ChartAccountDTO;
import com.ut.tekir.accounting.dto.ChartAccountSaveRequest;
import com.ut.tekir.accounting.dto.ChartAccountTreeDTO;
import com.ut.tekir.accounting.entity.ChartAccount;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChartAccountMapper {

    @Mapping(target = "planId", source = "plan.id")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentCode", source = "parent.code")
    ChartAccountDTO toDto(ChartAccount entity);

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "children", source = "children")
    ChartAccountTreeDTO toTreeDto(ChartAccount entity);

    List<ChartAccountDTO> toDtoList(List<ChartAccount> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plan", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    ChartAccount toEntity(ChartAccountSaveRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plan", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    void updateEntity(ChartAccountSaveRequest request, @MappingTarget ChartAccount entity);
}

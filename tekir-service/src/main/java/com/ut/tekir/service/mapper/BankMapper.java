package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.bank.BankAccountDTO;
import com.ut.tekir.common.dto.bank.BankBranchDTO;
import com.ut.tekir.common.dto.bank.BankDetailDTO;
import com.ut.tekir.common.dto.bank.BankListDTO;
import com.ut.tekir.common.entity.Bank;
import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.common.entity.BankBranch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for Bank, BankBranch, BankAccount ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface BankMapper {

    BankListDTO toListDTO(Bank entity);

    @Mapping(target = "branches", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    BankDetailDTO toDetailDTO(Bank entity);

    @Mapping(target = "bankId", source = "bank.id")
    BankBranchDTO toBranchDTO(BankBranch entity);
    List<BankBranchDTO> toBranchDTOList(List<BankBranch> list);

    @Mapping(target = "bankBranchId", source = "bankBranch.id")
    @Mapping(target = "bankBranchName", source = "bankBranch.name")
    @Mapping(target = "accountId", source = "account.id")
    BankAccountDTO toAccountDTO(BankAccount entity);
    List<BankAccountDTO> toAccountDTOList(List<BankAccount> list);
}

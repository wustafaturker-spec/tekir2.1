package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.bank.BankAccountDTO;
import com.ut.tekir.common.dto.bank.BankBranchDTO;
import com.ut.tekir.common.dto.bank.BankDetailDTO;
import com.ut.tekir.common.dto.bank.BankListDTO;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.Bank;
import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.common.entity.BankBranch;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:50+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class BankMapperImpl implements BankMapper {

    @Override
    public BankListDTO toListDTO(Bank entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;
        String swiftCode = null;
        Boolean active = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        swiftCode = entity.getSwiftCode();
        active = entity.getActive();

        BankListDTO bankListDTO = new BankListDTO( id, code, name, swiftCode, active );

        return bankListDTO;
    }

    @Override
    public BankDetailDTO toDetailDTO(Bank entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;
        String swiftCode = null;
        Boolean active = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        swiftCode = entity.getSwiftCode();
        active = entity.getActive();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();

        List<BankBranchDTO> branches = null;
        List<BankAccountDTO> accounts = null;

        BankDetailDTO bankDetailDTO = new BankDetailDTO( id, code, name, swiftCode, active, branches, accounts, createDate, createUser, updateDate );

        return bankDetailDTO;
    }

    @Override
    public BankBranchDTO toBranchDTO(BankBranch entity) {
        if ( entity == null ) {
            return null;
        }

        Long bankId = null;
        Long id = null;
        String code = null;
        String name = null;
        String eftCode = null;
        Boolean active = null;

        bankId = entityBankId( entity );
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        eftCode = entity.getEftCode();
        active = entity.getActive();

        BankBranchDTO bankBranchDTO = new BankBranchDTO( id, code, name, eftCode, active, bankId );

        return bankBranchDTO;
    }

    @Override
    public List<BankBranchDTO> toBranchDTOList(List<BankBranch> list) {
        if ( list == null ) {
            return null;
        }

        List<BankBranchDTO> list1 = new ArrayList<BankBranchDTO>( list.size() );
        for ( BankBranch bankBranch : list ) {
            list1.add( toBranchDTO( bankBranch ) );
        }

        return list1;
    }

    @Override
    public BankAccountDTO toAccountDTO(BankAccount entity) {
        if ( entity == null ) {
            return null;
        }

        Long bankBranchId = null;
        String bankBranchName = null;
        Long accountId = null;
        Long id = null;
        String name = null;
        String accountNo = null;
        String iban = null;
        String currency = null;
        Boolean active = null;

        bankBranchId = entityBankBranchId( entity );
        bankBranchName = entityBankBranchName( entity );
        accountId = entityAccountId( entity );
        id = entity.getId();
        name = entity.getName();
        accountNo = entity.getAccountNo();
        iban = entity.getIban();
        currency = entity.getCurrency();
        active = entity.getActive();

        BankAccountDTO bankAccountDTO = new BankAccountDTO( id, name, accountNo, iban, currency, bankBranchId, bankBranchName, accountId, active );

        return bankAccountDTO;
    }

    @Override
    public List<BankAccountDTO> toAccountDTOList(List<BankAccount> list) {
        if ( list == null ) {
            return null;
        }

        List<BankAccountDTO> list1 = new ArrayList<BankAccountDTO>( list.size() );
        for ( BankAccount bankAccount : list ) {
            list1.add( toAccountDTO( bankAccount ) );
        }

        return list1;
    }

    private Long entityBankId(BankBranch bankBranch) {
        if ( bankBranch == null ) {
            return null;
        }
        Bank bank = bankBranch.getBank();
        if ( bank == null ) {
            return null;
        }
        Long id = bank.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityBankBranchId(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }
        BankBranch bankBranch = bankAccount.getBankBranch();
        if ( bankBranch == null ) {
            return null;
        }
        Long id = bankBranch.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityBankBranchName(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }
        BankBranch bankBranch = bankAccount.getBankBranch();
        if ( bankBranch == null ) {
            return null;
        }
        String name = bankBranch.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityAccountId(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }
        Account account = bankAccount.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

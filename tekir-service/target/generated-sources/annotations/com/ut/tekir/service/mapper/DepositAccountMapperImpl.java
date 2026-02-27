package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.depositaccount.DepositAccountDetailDTO;
import com.ut.tekir.common.dto.depositaccount.DepositAccountListDTO;
import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.common.entity.DepositAccount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DepositAccountMapperImpl implements DepositAccountMapper {

    @Override
    public DepositAccountListDTO toListDTO(DepositAccount entity) {
        if ( entity == null ) {
            return null;
        }

        String bankAccountName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        bankAccountName = entityBankAccountName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        DepositAccountListDTO depositAccountListDTO = new DepositAccountListDTO( id, serial, code, date, bankAccountName, info );

        return depositAccountListDTO;
    }

    @Override
    public DepositAccountDetailDTO toDetailDTO(DepositAccount entity) {
        if ( entity == null ) {
            return null;
        }

        Long bankAccountId = null;
        String bankAccountName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;
        String reference = null;
        LocalDateTime createDate = null;
        String createUser = null;

        bankAccountId = entityBankAccountId( entity );
        bankAccountName = entityBankAccountName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();
        reference = entity.getReference();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        DepositAccountDetailDTO depositAccountDetailDTO = new DepositAccountDetailDTO( id, serial, code, date, bankAccountId, bankAccountName, info, reference, createDate, createUser );

        return depositAccountDetailDTO;
    }

    private String entityBankAccountName(DepositAccount depositAccount) {
        if ( depositAccount == null ) {
            return null;
        }
        BankAccount bankAccount = depositAccount.getBankAccount();
        if ( bankAccount == null ) {
            return null;
        }
        String name = bankAccount.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityBankAccountId(DepositAccount depositAccount) {
        if ( depositAccount == null ) {
            return null;
        }
        BankAccount bankAccount = depositAccount.getBankAccount();
        if ( bankAccount == null ) {
            return null;
        }
        Long id = bankAccount.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

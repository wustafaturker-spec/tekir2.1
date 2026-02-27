package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.cheque.ChequeDetailDTO;
import com.ut.tekir.common.dto.cheque.ChequeListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.Cheque;
import com.ut.tekir.common.entity.Contact;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ChequeMapperImpl implements ChequeMapper {

    @Override
    public ChequeListDTO toListDTO(Cheque entity) {
        if ( entity == null ) {
            return null;
        }

        String branchName = null;
        BigDecimal amount = null;
        String currency = null;
        LocalDate dueDate = null;
        String contactFullname = null;
        String referenceNo = null;
        String direction = null;
        String chequeNumber = null;
        Long id = null;
        String bankName = null;
        String accountNo = null;
        String chequeOwner = null;
        String info = null;

        branchName = entity.getBankBranch();
        amount = entityAmountValue( entity );
        currency = entityAmountCurrency( entity );
        dueDate = entity.getMaturityDate();
        contactFullname = entityContactName( entity );
        referenceNo = entity.getCode();
        if ( entity.getDirection() != null ) {
            direction = entity.getDirection().name();
        }
        chequeNumber = entity.getReference();
        id = entity.getId();
        bankName = entity.getBankName();
        accountNo = entity.getAccountNo();
        chequeOwner = entity.getChequeOwner();
        info = entity.getInfo();

        String status = entity.getStatus() != null ? entity.getStatus().name() : null;

        ChequeListDTO chequeListDTO = new ChequeListDTO( id, referenceNo, bankName, branchName, accountNo, chequeOwner, status, amount, currency, dueDate, contactFullname, info, direction, chequeNumber );

        return chequeListDTO;
    }

    @Override
    public ChequeDetailDTO toDetailDTO(Cheque entity) {
        if ( entity == null ) {
            return null;
        }

        String referenceNo = null;
        String branchName = null;
        MoneySetDTO money = null;
        LocalDate dueDate = null;
        String chequeNumber = null;
        Long contactId = null;
        Long id = null;
        String bankName = null;
        String accountNo = null;
        String chequeOwner = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;
        String updateUser = null;
        String direction = null;

        referenceNo = entity.getCode();
        branchName = entity.getBankBranch();
        money = moneySetToMoneySetDTO( entity.getAmount() );
        dueDate = entity.getMaturityDate();
        chequeNumber = entity.getReference();
        contactId = entityContactId( entity );
        id = entity.getId();
        bankName = entity.getBankName();
        accountNo = entity.getAccountNo();
        chequeOwner = entity.getChequeOwner();
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();
        updateUser = entity.getUpdateUser();
        if ( entity.getDirection() != null ) {
            direction = entity.getDirection().name();
        }

        String status = entity.getStatus() != null ? entity.getStatus().name() : null;
        LocalDate issueDate = null;
        Long bankId = null;
        Long bankBranchId = null;
        String contactCode = null;
        String contactFullname = null;

        ChequeDetailDTO chequeDetailDTO = new ChequeDetailDTO( id, referenceNo, status, bankId, bankName, bankBranchId, branchName, accountNo, chequeOwner, money, issueDate, dueDate, contactId, contactCode, contactFullname, info, createDate, createUser, updateDate, updateUser, direction, chequeNumber );

        return chequeDetailDTO;
    }

    private BigDecimal entityAmountValue(Cheque cheque) {
        if ( cheque == null ) {
            return null;
        }
        MoneySet amount = cheque.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityAmountCurrency(Cheque cheque) {
        if ( cheque == null ) {
            return null;
        }
        MoneySet amount = cheque.getAmount();
        if ( amount == null ) {
            return null;
        }
        String currency = amount.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private String entityContactName(Cheque cheque) {
        if ( cheque == null ) {
            return null;
        }
        Contact contact = cheque.getContact();
        if ( contact == null ) {
            return null;
        }
        String name = contact.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected MoneySetDTO moneySetToMoneySetDTO(MoneySet moneySet) {
        if ( moneySet == null ) {
            return null;
        }

        String currency = null;
        BigDecimal value = null;
        BigDecimal localAmount = null;

        currency = moneySet.getCurrency();
        value = moneySet.getValue();
        localAmount = moneySet.getLocalAmount();

        MoneySetDTO moneySetDTO = new MoneySetDTO( currency, value, localAmount );

        return moneySetDTO;
    }

    private Long entityContactId(Cheque cheque) {
        if ( cheque == null ) {
            return null;
        }
        Contact contact = cheque.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

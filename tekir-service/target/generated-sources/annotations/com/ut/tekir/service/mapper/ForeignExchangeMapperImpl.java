package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.foreignexchange.ForeignExchangeDetailDTO;
import com.ut.tekir.common.dto.foreignexchange.ForeignExchangeListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.common.entity.ForeignExchange;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ForeignExchangeMapperImpl implements ForeignExchangeMapper {

    @Override
    public ForeignExchangeListDTO toListDTO(ForeignExchange entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        MoneySetDTO fromAmount = null;
        MoneySetDTO toAmount = null;
        String info = null;

        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        fromAmount = moneySetToMoneySetDTO( entity.getFromAmount() );
        toAmount = moneySetToMoneySetDTO( entity.getToAmount() );
        info = entity.getInfo();

        ForeignExchangeListDTO foreignExchangeListDTO = new ForeignExchangeListDTO( id, serial, code, date, fromAmount, toAmount, info );

        return foreignExchangeListDTO;
    }

    @Override
    public ForeignExchangeDetailDTO toDetailDTO(ForeignExchange entity) {
        if ( entity == null ) {
            return null;
        }

        Long fromBankAccountId = null;
        String fromBankAccountName = null;
        Long toBankAccountId = null;
        String toBankAccountName = null;
        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        MoneySetDTO fromAmount = null;
        MoneySetDTO toAmount = null;
        BigDecimal exchangeRate = null;
        String info = null;
        String reference = null;
        LocalDateTime createDate = null;
        String createUser = null;

        fromBankAccountId = entityFromBankAccountId( entity );
        fromBankAccountName = entityFromBankAccountName( entity );
        toBankAccountId = entityToBankAccountId( entity );
        toBankAccountName = entityToBankAccountName( entity );
        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        fromAmount = moneySetToMoneySetDTO( entity.getFromAmount() );
        toAmount = moneySetToMoneySetDTO( entity.getToAmount() );
        exchangeRate = entity.getExchangeRate();
        info = entity.getInfo();
        reference = entity.getReference();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        ForeignExchangeDetailDTO foreignExchangeDetailDTO = new ForeignExchangeDetailDTO( id, serial, code, date, fromBankAccountId, fromBankAccountName, toBankAccountId, toBankAccountName, fromAmount, toAmount, exchangeRate, info, reference, createDate, createUser );

        return foreignExchangeDetailDTO;
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

    private Long entityFromBankAccountId(ForeignExchange foreignExchange) {
        if ( foreignExchange == null ) {
            return null;
        }
        BankAccount fromBankAccount = foreignExchange.getFromBankAccount();
        if ( fromBankAccount == null ) {
            return null;
        }
        Long id = fromBankAccount.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityFromBankAccountName(ForeignExchange foreignExchange) {
        if ( foreignExchange == null ) {
            return null;
        }
        BankAccount fromBankAccount = foreignExchange.getFromBankAccount();
        if ( fromBankAccount == null ) {
            return null;
        }
        String name = fromBankAccount.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityToBankAccountId(ForeignExchange foreignExchange) {
        if ( foreignExchange == null ) {
            return null;
        }
        BankAccount toBankAccount = foreignExchange.getToBankAccount();
        if ( toBankAccount == null ) {
            return null;
        }
        Long id = toBankAccount.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityToBankAccountName(ForeignExchange foreignExchange) {
        if ( foreignExchange == null ) {
            return null;
        }
        BankAccount toBankAccount = foreignExchange.getToBankAccount();
        if ( toBankAccount == null ) {
            return null;
        }
        String name = toBankAccount.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

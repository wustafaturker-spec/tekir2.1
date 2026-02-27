package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.banktransfer.BankTransferDetailDTO;
import com.ut.tekir.common.dto.banktransfer.BankTransferListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.BankToBankTransfer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:50+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class BankTransferMapperImpl implements BankTransferMapper {

    @Override
    public BankTransferListDTO toListDTO(BankToBankTransfer entity) {
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

        String transferType = entity.getTransferType() != null ? entity.getTransferType().name() : null;

        BankTransferListDTO bankTransferListDTO = new BankTransferListDTO( id, serial, code, date, transferType, fromAmount, toAmount, info );

        return bankTransferListDTO;
    }

    @Override
    public BankTransferDetailDTO toDetailDTO(BankToBankTransfer entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        MoneySetDTO fromAmount = null;
        MoneySetDTO toAmount = null;
        MoneySetDTO cost = null;
        String info = null;
        String reference = null;
        LocalDateTime createDate = null;
        String createUser = null;

        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        fromAmount = moneySetToMoneySetDTO( entity.getFromAmount() );
        toAmount = moneySetToMoneySetDTO( entity.getToAmount() );
        cost = moneySetToMoneySetDTO( entity.getCost() );
        info = entity.getInfo();
        reference = entity.getReference();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        String transferType = entity.getTransferType() != null ? entity.getTransferType().name() : null;

        BankTransferDetailDTO bankTransferDetailDTO = new BankTransferDetailDTO( id, serial, code, date, transferType, fromAmount, toAmount, cost, info, reference, createDate, createUser );

        return bankTransferDetailDTO;
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
}

package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditVirementDetailDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditVirementListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.DebitCreditVirement;
import com.ut.tekir.common.entity.DebitCreditVirementItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DebitCreditVirementMapperImpl implements DebitCreditVirementMapper {

    @Override
    public DebitCreditVirementListDTO toListDTO(DebitCreditVirement entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        DebitCreditVirementListDTO debitCreditVirementListDTO = new DebitCreditVirementListDTO( id, serial, code, date, info );

        return debitCreditVirementListDTO;
    }

    @Override
    public DebitCreditVirementDetailDTO toDetailDTO(DebitCreditVirement entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String serial = null;
        String code = null;
        LocalDate date = null;
        String info = null;
        String reference = null;
        List<DebitCreditVirementDetailDTO.VirementItemDTO> items = null;
        LocalDateTime createDate = null;
        String createUser = null;

        id = entity.getId();
        serial = entity.getSerial();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();
        reference = entity.getReference();
        items = toItemDTOList( entity.getItems() );
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        DebitCreditVirementDetailDTO debitCreditVirementDetailDTO = new DebitCreditVirementDetailDTO( id, serial, code, date, info, reference, items, createDate, createUser );

        return debitCreditVirementDetailDTO;
    }

    @Override
    public DebitCreditVirementDetailDTO.VirementItemDTO toItemDTO(DebitCreditVirementItem item) {
        if ( item == null ) {
            return null;
        }

        Long id = null;
        MoneySetDTO amount = null;
        String info = null;

        id = item.getId();
        amount = moneySetToMoneySetDTO( item.getAmount() );
        info = item.getInfo();

        DebitCreditVirementDetailDTO.VirementItemDTO virementItemDTO = new DebitCreditVirementDetailDTO.VirementItemDTO( id, amount, info );

        return virementItemDTO;
    }

    @Override
    public List<DebitCreditVirementDetailDTO.VirementItemDTO> toItemDTOList(List<DebitCreditVirementItem> items) {
        if ( items == null ) {
            return null;
        }

        List<DebitCreditVirementDetailDTO.VirementItemDTO> list = new ArrayList<DebitCreditVirementDetailDTO.VirementItemDTO>( items.size() );
        for ( DebitCreditVirementItem debitCreditVirementItem : items ) {
            list.add( toItemDTO( debitCreditVirementItem ) );
        }

        return list;
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

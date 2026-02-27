package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.payment.PaymentDetailDTO;
import com.ut.tekir.common.dto.payment.PaymentItemDTO;
import com.ut.tekir.common.dto.payment.PaymentListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.Payment;
import com.ut.tekir.common.entity.PaymentItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentListDTO toListDTO(Payment entity) {
        if ( entity == null ) {
            return null;
        }

        String contactCode = null;
        String contactFullname = null;
        Long id = null;
        String code = null;
        LocalDate date = null;
        String info = null;

        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        id = entity.getId();
        code = entity.getCode();
        date = entity.getDate();
        info = entity.getInfo();

        String paymentType = entity.getAction() != null ? entity.getAction().name() : null;
        BigDecimal totalAmount = calculateTotal(entity);
        String currency = getFirstCurrency(entity);

        PaymentListDTO paymentListDTO = new PaymentListDTO( id, code, date, paymentType, contactCode, contactFullname, totalAmount, currency, info );

        return paymentListDTO;
    }

    @Override
    public PaymentDetailDTO toDetailDTO(Payment entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        String contactCode = null;
        String contactFullname = null;
        Long accountId = null;
        String accountName = null;
        Long id = null;
        String code = null;
        LocalDate date = null;
        List<PaymentItemDTO> items = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;

        contactId = entityContactId( entity );
        contactCode = entityContactCode( entity );
        contactFullname = entityContactFullname( entity );
        accountId = entityAccountId( entity );
        accountName = entityAccountName( entity );
        id = entity.getId();
        code = entity.getCode();
        date = entity.getDate();
        items = toItemDTOList( entity.getItems() );
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();

        String paymentType = entity.getAction() != null ? entity.getAction().name() : null;
        MoneySetDTO totalAmount = null;

        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO( id, code, date, paymentType, contactId, contactCode, contactFullname, accountId, accountName, totalAmount, items, info, createDate, createUser, updateDate );

        return paymentDetailDTO;
    }

    @Override
    public PaymentItemDTO toItemDTO(PaymentItem entity) {
        if ( entity == null ) {
            return null;
        }

        BigDecimal amount = null;
        String currency = null;
        BigDecimal localAmount = null;
        Long id = null;
        String info = null;

        amount = entityAmountValue( entity );
        currency = entityAmountCurrency( entity );
        localAmount = entityAmountLocalAmount( entity );
        id = entity.getId();
        info = entity.getInfo();

        Integer lineNo = null;
        String financeAction = null;
        Long accountId = null;
        String accountName = null;
        Long bankAccountId = null;
        String bankAccountName = null;

        PaymentItemDTO paymentItemDTO = new PaymentItemDTO( id, lineNo, financeAction, amount, currency, localAmount, accountId, accountName, bankAccountId, bankAccountName, info );

        return paymentItemDTO;
    }

    @Override
    public List<PaymentItemDTO> toItemDTOList(List<PaymentItem> items) {
        if ( items == null ) {
            return null;
        }

        List<PaymentItemDTO> list = new ArrayList<PaymentItemDTO>( items.size() );
        for ( PaymentItem paymentItem : items ) {
            list.add( toItemDTO( paymentItem ) );
        }

        return list;
    }

    private String entityContactCode(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Contact contact = payment.getContact();
        if ( contact == null ) {
            return null;
        }
        String code = contact.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityContactFullname(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Contact contact = payment.getContact();
        if ( contact == null ) {
            return null;
        }
        String fullname = contact.getFullname();
        if ( fullname == null ) {
            return null;
        }
        return fullname;
    }

    private Long entityContactId(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Contact contact = payment.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityAccountId(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Account account = payment.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAccountName(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Account account = payment.getAccount();
        if ( account == null ) {
            return null;
        }
        String name = account.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal entityAmountValue(PaymentItem paymentItem) {
        if ( paymentItem == null ) {
            return null;
        }
        MoneySet amount = paymentItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityAmountCurrency(PaymentItem paymentItem) {
        if ( paymentItem == null ) {
            return null;
        }
        MoneySet amount = paymentItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        String currency = amount.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private BigDecimal entityAmountLocalAmount(PaymentItem paymentItem) {
        if ( paymentItem == null ) {
            return null;
        }
        MoneySet amount = paymentItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal localAmount = amount.getLocalAmount();
        if ( localAmount == null ) {
            return null;
        }
        return localAmount;
    }
}

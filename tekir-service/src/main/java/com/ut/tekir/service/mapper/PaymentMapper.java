package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.payment.PaymentDetailDTO;
import com.ut.tekir.common.dto.payment.PaymentItemDTO;
import com.ut.tekir.common.dto.payment.PaymentListDTO;
import com.ut.tekir.common.entity.Payment;
import com.ut.tekir.common.entity.PaymentItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * MapStruct mapper for Payment entity to DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "paymentType", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "totalAmount", expression = "java(calculateTotal(entity))")
    @Mapping(target = "currency", expression = "java(getFirstCurrency(entity))")
    PaymentListDTO toListDTO(Payment entity);

    @Mapping(target = "paymentType", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "accountName", source = "account.name")
    @Mapping(target = "totalAmount", ignore = true)
    PaymentDetailDTO toDetailDTO(Payment entity);

    @Mapping(target = "lineNo", ignore = true)
    @Mapping(target = "financeAction", ignore = true)
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "currency", source = "amount.currency")
    @Mapping(target = "localAmount", source = "amount.localAmount")
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "accountName", ignore = true)
    @Mapping(target = "bankAccountId", ignore = true)
    @Mapping(target = "bankAccountName", ignore = true)
    PaymentItemDTO toItemDTO(PaymentItem entity);

    List<PaymentItemDTO> toItemDTOList(List<PaymentItem> items);

    default BigDecimal calculateTotal(Payment entity) {
        if (entity.getItems() == null || entity.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return entity.getItems().stream()
            .map(item -> item.getAmount() != null && item.getAmount().getValue() != null
                ? item.getAmount().getValue() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default String getFirstCurrency(Payment entity) {
        if (entity.getItems() == null || entity.getItems().isEmpty()) {
            return "TRL";
        }
        return entity.getItems().stream()
            .filter(item -> item.getAmount() != null && item.getAmount().getCurrency() != null)
            .map(item -> item.getAmount().getCurrency())
            .findFirst()
            .orElse("TRL");
    }
}

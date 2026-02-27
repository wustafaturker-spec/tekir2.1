package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.order.OrderDetailDTO;
import com.ut.tekir.common.dto.order.OrderItemDTO;
import com.ut.tekir.common.dto.order.OrderListDTO;
import com.ut.tekir.common.entity.OrderNote;
import com.ut.tekir.common.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for OrderNote entity ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "tradeAction", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "grandTotalValue", ignore = true)
    @Mapping(target = "grandTotalCurrency", ignore = true)
    OrderListDTO toListDTO(OrderNote entity);

    @Mapping(target = "tradeAction", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    @Mapping(target = "grandTotal", ignore = true)
    OrderDetailDTO toDetailDTO(OrderNote entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productCode", source = "product.code")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "quantity", source = "quantity.value")
    @Mapping(target = "unitName", source = "quantity.unit")
    @Mapping(target = "unitPrice", source = "unitPrice.value")
    @Mapping(target = "currency", source = "unitPrice.currency")
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "taxRate", source = "tax.rate")
    @Mapping(target = "discountRate", ignore = true)
    @Mapping(target = "lineTotal", source = "amount.localAmount")
    @Mapping(target = "lineNo", ignore = true)
    OrderItemDTO toItemDTO(OrderItem entity);

    List<OrderItemDTO> toItemDTOList(List<OrderItem> items);
}

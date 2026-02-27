package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.shipment.ShipmentItemDTO;
import com.ut.tekir.common.dto.shipment.ShipmentNoteDetailDTO;
import com.ut.tekir.common.dto.shipment.ShipmentNoteListDTO;
import com.ut.tekir.common.entity.ShipmentItem;
import com.ut.tekir.common.entity.ShipmentNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for ShipmentNote ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface ShipmentNoteMapper {

    @Mapping(target = "tradeAction", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    ShipmentNoteListDTO toListDTO(ShipmentNote entity);

    @Mapping(target = "tradeAction", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.fullname")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    ShipmentNoteDetailDTO toDetailDTO(ShipmentNote entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productCode", source = "product.code")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "quantity", source = "quantity.value")
    @Mapping(target = "unitName", source = "quantity.unit")
    @Mapping(target = "unitPrice", source = "unitPrice.value")
    @Mapping(target = "currency", source = "unitPrice.currency")
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "taxRate", source = "tax.rate")
    ShipmentItemDTO toItemDTO(ShipmentItem entity);

    List<ShipmentItemDTO> toItemDTOList(List<ShipmentItem> items);
}

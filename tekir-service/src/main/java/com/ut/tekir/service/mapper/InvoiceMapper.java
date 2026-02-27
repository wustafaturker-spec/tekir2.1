package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.invoice.InvoiceDetailDTO;
import com.ut.tekir.common.dto.invoice.InvoiceItemDTO;
import com.ut.tekir.common.dto.invoice.InvoiceListDTO;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.entity.InvoiceItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for Invoice entity ↔ DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "tradeAction", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.name")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    @Mapping(target = "grandTotalValue", source = "grandTotal.value")
    @Mapping(target = "grandTotalCurrency", source = "grandTotal.currency")
    @Mapping(target = "beforeTaxValue", source = "beforeTax.value")
    @Mapping(target = "totalTaxValue", source = "totalTax.value")
    InvoiceListDTO toListDTO(Invoice entity);

    @Mapping(target = "tradeAction", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "contactCode", source = "contact.code")
    @Mapping(target = "contactFullname", source = "contact.name")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "warehouseName", source = "warehouse.name")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "accountName", source = "account.name")
    InvoiceDetailDTO toDetailDTO(Invoice entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productCode", source = "product.code")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "quantity", source = "quantity.value")
    @Mapping(target = "unitName", source = "quantity.unit")
    @Mapping(target = "unitPrice", source = "unitPrice.value")
    @Mapping(target = "currency", source = "unitPrice.currency")
    @Mapping(target = "amount", source = "amount.value")
    @Mapping(target = "taxRate", source = "tax.rate")
    @Mapping(target = "taxAmount", source = "taxAmount.value")
    @Mapping(target = "discountRate", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "lineTotal", source = "amount.localAmount")
    @Mapping(target = "lineNo", ignore = true)
    InvoiceItemDTO toItemDTO(InvoiceItem entity);

    List<InvoiceItemDTO> toItemDTOList(List<InvoiceItem> items);
}

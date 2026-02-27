package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.MoneySetDTO;
import com.ut.tekir.common.dto.invoice.InvoiceDetailDTO;
import com.ut.tekir.common.dto.invoice.InvoiceItemDTO;
import com.ut.tekir.common.dto.invoice.InvoiceListDTO;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.embeddable.TaxEmbeddable;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.entity.InvoiceItem;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.Warehouse;
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
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public InvoiceListDTO toListDTO(Invoice entity) {
        if ( entity == null ) {
            return null;
        }

        String contactCode = null;
        String contactFullname = null;
        String warehouseName = null;
        BigDecimal grandTotalValue = null;
        String grandTotalCurrency = null;
        BigDecimal beforeTaxValue = null;
        BigDecimal totalTaxValue = null;
        Long id = null;
        String code = null;
        String serial = null;
        String reference = null;
        LocalDate date = null;
        String info = null;

        contactCode = entityContactCode( entity );
        contactFullname = entityContactName( entity );
        warehouseName = entityWarehouseName( entity );
        grandTotalValue = entityGrandTotalValue( entity );
        grandTotalCurrency = entityGrandTotalCurrency( entity );
        beforeTaxValue = entityBeforeTaxValue( entity );
        totalTaxValue = entityTotalTaxValue( entity );
        id = entity.getId();
        code = entity.getCode();
        serial = entity.getSerial();
        reference = entity.getReference();
        date = entity.getDate();
        info = entity.getInfo();

        String tradeAction = entity.getAction() != null ? entity.getAction().name() : null;

        InvoiceListDTO invoiceListDTO = new InvoiceListDTO( id, code, serial, reference, date, tradeAction, contactCode, contactFullname, warehouseName, grandTotalValue, grandTotalCurrency, beforeTaxValue, totalTaxValue, info );

        return invoiceListDTO;
    }

    @Override
    public InvoiceDetailDTO toDetailDTO(Invoice entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        String contactCode = null;
        String contactFullname = null;
        Long warehouseId = null;
        String warehouseName = null;
        Long accountId = null;
        String accountName = null;
        Long id = null;
        String code = null;
        String serial = null;
        String reference = null;
        LocalDate date = null;
        MoneySetDTO beforeTax = null;
        MoneySetDTO totalTax = null;
        MoneySetDTO grandTotal = null;
        MoneySetDTO totalFee = null;
        MoneySetDTO totalExpense = null;
        BigDecimal taxExcludedTotalLcyVal = null;
        BigDecimal totalDiscountLcyVal = null;
        BigDecimal totalDocumentDiscountLcyVal = null;
        List<InvoiceItemDTO> items = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;

        contactId = entityContactId( entity );
        contactCode = entityContactCode( entity );
        contactFullname = entityContactName( entity );
        warehouseId = entityWarehouseId( entity );
        warehouseName = entityWarehouseName( entity );
        accountId = entityAccountId( entity );
        accountName = entityAccountName( entity );
        id = entity.getId();
        code = entity.getCode();
        serial = entity.getSerial();
        reference = entity.getReference();
        date = entity.getDate();
        beforeTax = moneySetToMoneySetDTO( entity.getBeforeTax() );
        totalTax = moneySetToMoneySetDTO( entity.getTotalTax() );
        grandTotal = moneySetToMoneySetDTO( entity.getGrandTotal() );
        totalFee = moneySetToMoneySetDTO( entity.getTotalFee() );
        totalExpense = moneySetToMoneySetDTO( entity.getTotalExpense() );
        taxExcludedTotalLcyVal = entity.getTaxExcludedTotalLcyVal();
        totalDiscountLcyVal = entity.getTotalDiscountLcyVal();
        totalDocumentDiscountLcyVal = entity.getTotalDocumentDiscountLcyVal();
        items = toItemDTOList( entity.getItems() );
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();

        String tradeAction = entity.getAction() != null ? entity.getAction().name() : null;

        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO( id, code, serial, reference, date, tradeAction, contactId, contactCode, contactFullname, warehouseId, warehouseName, accountId, accountName, beforeTax, totalTax, grandTotal, totalFee, totalExpense, taxExcludedTotalLcyVal, totalDiscountLcyVal, totalDocumentDiscountLcyVal, items, info, createDate, createUser, updateDate );

        return invoiceDetailDTO;
    }

    @Override
    public InvoiceItemDTO toItemDTO(InvoiceItem entity) {
        if ( entity == null ) {
            return null;
        }

        Long productId = null;
        String productCode = null;
        String productName = null;
        BigDecimal quantity = null;
        String unitName = null;
        BigDecimal unitPrice = null;
        String currency = null;
        BigDecimal amount = null;
        BigDecimal taxRate = null;
        BigDecimal taxAmount = null;
        BigDecimal lineTotal = null;
        Long id = null;
        String info = null;

        productId = entityProductId( entity );
        productCode = entityProductCode( entity );
        productName = entityProductName( entity );
        quantity = entityQuantityValue( entity );
        unitName = entityQuantityUnit( entity );
        unitPrice = entityUnitPriceValue( entity );
        currency = entityUnitPriceCurrency( entity );
        amount = entityAmountValue( entity );
        taxRate = entityTaxRate( entity );
        taxAmount = entityTaxAmountValue( entity );
        lineTotal = entityAmountLocalAmount( entity );
        id = entity.getId();
        info = entity.getInfo();

        BigDecimal discountRate = null;
        BigDecimal discountAmount = null;
        Integer lineNo = null;

        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO( id, lineNo, productId, productCode, productName, quantity, unitName, unitPrice, currency, amount, taxRate, taxAmount, discountRate, discountAmount, lineTotal, info );

        return invoiceItemDTO;
    }

    @Override
    public List<InvoiceItemDTO> toItemDTOList(List<InvoiceItem> items) {
        if ( items == null ) {
            return null;
        }

        List<InvoiceItemDTO> list = new ArrayList<InvoiceItemDTO>( items.size() );
        for ( InvoiceItem invoiceItem : items ) {
            list.add( toItemDTO( invoiceItem ) );
        }

        return list;
    }

    private String entityContactCode(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Contact contact = invoice.getContact();
        if ( contact == null ) {
            return null;
        }
        String code = contact.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityContactName(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Contact contact = invoice.getContact();
        if ( contact == null ) {
            return null;
        }
        String name = contact.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityWarehouseName(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Warehouse warehouse = invoice.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        String name = warehouse.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal entityGrandTotalValue(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        MoneySet grandTotal = invoice.getGrandTotal();
        if ( grandTotal == null ) {
            return null;
        }
        BigDecimal value = grandTotal.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityGrandTotalCurrency(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        MoneySet grandTotal = invoice.getGrandTotal();
        if ( grandTotal == null ) {
            return null;
        }
        String currency = grandTotal.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private BigDecimal entityBeforeTaxValue(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        MoneySet beforeTax = invoice.getBeforeTax();
        if ( beforeTax == null ) {
            return null;
        }
        BigDecimal value = beforeTax.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private BigDecimal entityTotalTaxValue(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        MoneySet totalTax = invoice.getTotalTax();
        if ( totalTax == null ) {
            return null;
        }
        BigDecimal value = totalTax.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private Long entityContactId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Contact contact = invoice.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityWarehouseId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Warehouse warehouse = invoice.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        Long id = warehouse.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityAccountId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Account account = invoice.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAccountName(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Account account = invoice.getAccount();
        if ( account == null ) {
            return null;
        }
        String name = account.getName();
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

    private Long entityProductId(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        Product product = invoiceItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProductCode(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        Product product = invoiceItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String code = product.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String entityProductName(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        Product product = invoiceItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal entityQuantityValue(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        Quantity quantity = invoiceItem.getQuantity();
        if ( quantity == null ) {
            return null;
        }
        BigDecimal value = quantity.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityQuantityUnit(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        Quantity quantity = invoiceItem.getQuantity();
        if ( quantity == null ) {
            return null;
        }
        String unit = quantity.getUnit();
        if ( unit == null ) {
            return null;
        }
        return unit;
    }

    private BigDecimal entityUnitPriceValue(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        MoneySet unitPrice = invoiceItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        BigDecimal value = unitPrice.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String entityUnitPriceCurrency(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        MoneySet unitPrice = invoiceItem.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        String currency = unitPrice.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency;
    }

    private BigDecimal entityAmountValue(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        MoneySet amount = invoiceItem.getAmount();
        if ( amount == null ) {
            return null;
        }
        BigDecimal value = amount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private BigDecimal entityTaxRate(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        TaxEmbeddable tax = invoiceItem.getTax();
        if ( tax == null ) {
            return null;
        }
        BigDecimal rate = tax.getRate();
        if ( rate == null ) {
            return null;
        }
        return rate;
    }

    private BigDecimal entityTaxAmountValue(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        MoneySet taxAmount = invoiceItem.getTaxAmount();
        if ( taxAmount == null ) {
            return null;
        }
        BigDecimal value = taxAmount.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private BigDecimal entityAmountLocalAmount(InvoiceItem invoiceItem) {
        if ( invoiceItem == null ) {
            return null;
        }
        MoneySet amount = invoiceItem.getAmount();
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

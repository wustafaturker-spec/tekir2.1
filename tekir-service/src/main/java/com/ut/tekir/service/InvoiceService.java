package com.ut.tekir.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.invoice.InvoiceDetailDTO;
import com.ut.tekir.common.dto.invoice.InvoiceFilterModel;
import com.ut.tekir.common.dto.invoice.InvoiceItemSaveRequest;
import com.ut.tekir.common.dto.invoice.InvoiceListDTO;
import com.ut.tekir.common.dto.invoice.InvoiceSaveRequest;
import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.entity.InvoiceItem;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.ProductTxn;
import com.ut.tekir.common.entity.Warehouse;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.ContactAddress;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction;
import com.ut.tekir.common.enums.TradeAction;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.AccountRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.repository.InvoiceRepository;
import com.ut.tekir.repository.ProductRepository;
import com.ut.tekir.repository.ProductTxnRepository;
import com.ut.tekir.repository.WarehouseRepository;
import com.ut.tekir.repository.FinanceTxnRepository;
import com.ut.tekir.common.entity.FinanceTxn;
import com.ut.tekir.service.einvoice.TaxPayerCheckService;
import com.ut.tekir.service.mapper.InvoiceMapper;
import com.ut.tekir.service.spec.InvoiceSpecifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ContactRepository contactRepository;
    private final WarehouseRepository warehouseRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final ProductTxnRepository productTxnRepository;
    private final InvoiceMapper invoiceMapper;
    private final SequenceService sequenceService;
    private final TaxPayerCheckService taxPayerCheckService;
    private final FinanceTxnRepository financeTxnRepository;

    @Transactional(readOnly = true)
    public Page<InvoiceListDTO> search(InvoiceFilterModel filter, Pageable pageable) {
        return invoiceRepository
                .findAll(InvoiceSpecifications.withFilter(filter), pageable)
                .map(invoiceMapper::toListDTO);
    }

    @Transactional(readOnly = true)
    public InvoiceDetailDTO getById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice", id));
        return invoiceMapper.toDetailDTO(invoice);
    }

    public InvoiceDetailDTO save(InvoiceSaveRequest request) {
        Invoice invoice;

        if (request.id() != null) {
            invoice = invoiceRepository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Invoice", request.id()));
            // Clear old items for replacement
            invoice.getItems().clear();
        } else {
            invoice = new Invoice();
            // Use unified sequence for code (unique constraint spans all invoice types)
            invoice.setCode(sequenceService.getNewNumber("Invoice", 6));
            String actionPrefix = StringUtils.hasText(request.tradeAction())
                    ? request.tradeAction()
                    : "Invoice";
            invoice.setSerial(sequenceService.getNewNumber(actionPrefix + ".serial", 6));
            invoice.setDate(request.date() != null ? request.date() : LocalDate.now());
        }

        // Map basic fields
        if (StringUtils.hasText(request.tradeAction())) {
            invoice.setAction(TradeAction.valueOf(request.tradeAction()));
        }
        if (StringUtils.hasText(request.serial())) {
            invoice.setSerial(request.serial());
        }
        if (StringUtils.hasText(request.reference())) {
            invoice.setReference(request.reference());
        }
        if (request.date() != null) {
            invoice.setDate(request.date());
        }
        invoice.setInfo(request.info());

        // Set contact
        Contact contact = contactRepository.findById(request.contactId())
                .orElseThrow(() -> new EntityNotFoundException("Contact", request.contactId()));
        invoice.setContact(contact);

        // Automatic e-Invoice check and type determination
        taxPayerCheckService.checkAndUpdateContact(contact);
        invoice.setEInvoiceType(taxPayerCheckService.determineDocumentType(contact));
        invoice.setEInvoiceScenario(contact.getInvoiceScenario());
        if (invoice.getEInvoiceStatus() == com.ut.tekir.common.enums.EInvoiceStatus.NONE) {
            invoice.setEInvoiceStatus(com.ut.tekir.common.enums.EInvoiceStatus.DRAFT);
        }

        // Set warehouse
        if (request.warehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse", request.warehouseId()));
            invoice.setWarehouse(warehouse);
        } else {
            invoice.setWarehouse(null);
        }

        // Set account
        if (request.accountId() != null) {
            Account account = accountRepository.findById(request.accountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account", request.accountId()));
            invoice.setAccount(account);
        } else {
            invoice.setAccount(null);
        }

        // Map items
        if (request.items() != null) {
            for (InvoiceItemSaveRequest itemReq : request.items()) {
                InvoiceItem item = new InvoiceItem();
                item.setOwner(invoice);

                Product product = productRepository.findById(itemReq.productId())
                        .orElseThrow(() -> new EntityNotFoundException("Product", itemReq.productId()));
                item.setProduct(product);

                item.getQuantity().setValue(itemReq.quantity());
                String ccy = StringUtils.hasText(itemReq.currency()) ? itemReq.currency() : "TRY";
                item.getUnitPrice().setValue(itemReq.unitPrice());
                item.getUnitPrice().setCurrency(ccy);
                item.getUnitPrice().setLocalAmount(itemReq.unitPrice());

                // amount = quantity * unitPrice
                BigDecimal amount = itemReq.quantity().multiply(itemReq.unitPrice())
                        .setScale(2, RoundingMode.HALF_UP);
                item.getAmount().setValue(amount);
                item.getAmount().setCurrency(ccy);
                item.getAmount().setLocalAmount(amount);

                // Tax
                if (itemReq.taxRate() != null) {
                    item.getTax().setRate(itemReq.taxRate());
                }

                item.setInfo(itemReq.info());
                invoice.getItems().add(item);
            }
        }

        // Snapshot delivery address
        snapshotDeliveryAddress(invoice);

        // Calculate totals
        calculateInvoice(invoice);

        invoice = invoiceRepository.save(invoice);

        // Create stock transactions (PRODUCT_TXN) for each item
        createStockTransactions(invoice);

        // Create finance transactions (FINANCE_TXN) for the contact
        createFinanceTransactions(invoice);

        return invoiceMapper.toDetailDTO(invoice);
    }

    public void delete(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Invoice", id);
        }
        // Clean up stock transactions before deleting invoice
        productTxnRepository.deleteByDocumentId(id);
        // Clean up finance transactions
        financeTxnRepository.deleteByDocumentIdAndDocumentType(id, DocumentType.SaleInvoice);
        financeTxnRepository.deleteByDocumentIdAndDocumentType(id, DocumentType.PurchaseInvoice);

        invoiceRepository.deleteById(id);
    }

    public InvoiceDetailDTO approve(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice", id));
        invoice.setActive(Boolean.TRUE);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDetailDTO(invoice);
    }

    private void createStockTransactions(Invoice invoice) {
        // Delete existing stock transactions for this invoice (handles edits)
        productTxnRepository.deleteByDocumentId(invoice.getId());

        if (invoice.getAction() == null || invoice.getItems() == null)
            return;

        // Purchase = stock IN (Debit), Sale = stock OUT (Credit)
        FinanceAction action = invoice.getAction() == TradeAction.Purchase
                ? FinanceAction.Debit
                : FinanceAction.Credit;

        DocumentType docType = invoice.getAction() == TradeAction.Purchase
                ? DocumentType.PurchaseInvoice
                : DocumentType.SaleInvoice;

        for (InvoiceItem item : invoice.getItems()) {
            if (item.getProduct() == null)
                continue;
            BigDecimal qty = item.getQuantity() != null ? item.getQuantity().getValue() : null;
            if (qty == null || qty.compareTo(BigDecimal.ZERO) == 0)
                continue;

            ProductTxn txn = new ProductTxn();
            txn.setProduct(item.getProduct());
            txn.setWarehouse(invoice.getWarehouse());
            txn.setAction(action);
            txn.setDocumentType(docType);
            txn.setDocumentId(invoice.getId());
            txn.setDate(invoice.getDate());
            txn.setQuantity(new Quantity(qty,
                    item.getQuantity().getUnit() != null ? item.getQuantity().getUnit() : ""));
            txn.setUnitPrice(item.getUnitPrice() != null ? item.getUnitPrice().getValue() : BigDecimal.ZERO);
            txn.setReference(invoice.getSerial());
            productTxnRepository.save(txn);
        }
    }

    private void createFinanceTransactions(Invoice invoice) {
        // Delete existing finance transactions for this document
        financeTxnRepository.deleteByDocumentIdAndDocumentType(invoice.getId(), DocumentType.SaleInvoice);
        financeTxnRepository.deleteByDocumentIdAndDocumentType(invoice.getId(), DocumentType.PurchaseInvoice);

        if (invoice.getAction() == null || invoice.getContact() == null)
            return;
        if (invoice.getGrandTotal() == null || invoice.getGrandTotal().getValue() == null)
            return;
        if (invoice.getGrandTotal().getValue().compareTo(BigDecimal.ZERO) == 0)
            return;

        // Sale = Customer owes us (Debit/Borç)
        // Purchase = We owe vendor (Credit/Alacak)
        FinanceAction action = invoice.getAction() == TradeAction.Sale
                ? FinanceAction.Debit
                : FinanceAction.Credit;

        DocumentType docType = invoice.getAction() == TradeAction.Sale
                ? DocumentType.SaleInvoice
                : DocumentType.PurchaseInvoice;

        FinanceTxn txn = new FinanceTxn();
        txn.setContact(invoice.getContact());
        txn.setAction(action);
        txn.setDocumentType(docType);
        txn.setDocumentId(invoice.getId());
        txn.setCode(invoice.getCode());
        txn.setDate(invoice.getDate() != null ? invoice.getDate() : LocalDate.now());
        txn.setInfo(invoice.getInfo());

        // Amount mapping
        String currency = invoice.getGrandTotal().getCurrency() != null ? invoice.getGrandTotal().getCurrency() : "TRY";
        BigDecimal val = invoice.getGrandTotal().getValue();
        txn.getAmount().setValue(val);
        txn.getAmount().setCurrency(currency);
        txn.getAmount().setLocalAmount(val); // Assuming rate=1 for now, or fetch from currency service

        financeTxnRepository.save(txn);
    }

    public void rebuildFinanceTransactions() {
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice : invoices) {
            createFinanceTransactions(invoice);
        }
    }

    private void snapshotDeliveryAddress(Invoice invoice) {
        if (invoice.getDeliveryAddress().getStreet() == null && invoice.getContact() != null
                && invoice.getContact().getAddressList() != null) {
            ContactAddress addr = invoice.getContact().getAddressList().stream()
                    .filter(a -> Boolean.TRUE.equals(a.getDefaultAddress()))
                    .findFirst()
                    .orElse(invoice.getContact().getAddressList().stream()
                            .filter(a -> Boolean.TRUE.equals(a.getActiveAddress()))
                            .findFirst()
                            .orElse(null));

            if (addr != null && addr.getAddress() != null) {
                invoice.setDeliveryAddress(addr.getAddress());
            }
        }
    }

    private void calculateInvoice(Invoice invoice) {
        BigDecimal totalBeforeTax = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        String currency = "TRY";

        if (!invoice.getItems().isEmpty()) {
            String itemCcy = invoice.getItems().get(0).getAmount().getCurrency();
            if (itemCcy != null)
                currency = itemCcy;
        }

        for (InvoiceItem item : invoice.getItems()) {
            BigDecimal amount = item.getAmount().getValue();
            if (amount == null)
                amount = BigDecimal.ZERO;

            BigDecimal taxRate = item.getTax().getRate();
            if (taxRate == null)
                taxRate = BigDecimal.ZERO;

            boolean vatIncluded = Boolean.TRUE.equals(item.getTax().getVatIncluded());
            BigDecimal itemTax;
            BigDecimal beforeTaxVal;

            if (vatIncluded) {
                BigDecimal divisor = taxRate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP)
                        .add(BigDecimal.ONE);
                beforeTaxVal = amount.divide(divisor, 2, RoundingMode.HALF_UP);
                itemTax = amount.subtract(beforeTaxVal);
            } else {
                beforeTaxVal = amount;
                itemTax = amount.multiply(taxRate)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            }

            item.getBeforeTaxAmount().setValue(beforeTaxVal);
            item.getBeforeTaxAmount().setCurrency(currency);
            item.getBeforeTaxAmount().setLocalAmount(beforeTaxVal);

            item.getTaxAmount().setValue(itemTax);
            item.getTaxAmount().setCurrency(currency);
            item.getTaxAmount().setLocalAmount(itemTax);

            totalBeforeTax = totalBeforeTax.add(beforeTaxVal);
            totalTax = totalTax.add(itemTax);
        }

        invoice.getBeforeTax().setValue(totalBeforeTax);
        invoice.getBeforeTax().setCurrency(currency);
        invoice.getBeforeTax().setLocalAmount(totalBeforeTax);

        invoice.getTotalTax().setValue(totalTax);
        invoice.getTotalTax().setCurrency(currency);
        invoice.getTotalTax().setLocalAmount(totalTax);

        BigDecimal grandTotal = totalBeforeTax.add(totalTax);
        invoice.getGrandTotal().setValue(grandTotal);
        invoice.getGrandTotal().setCurrency(currency);
        invoice.getGrandTotal().setLocalAmount(grandTotal);
    }
}

package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.contact.ContactDetailDTO;
import com.ut.tekir.common.dto.contact.ContactFilterModel;
import com.ut.tekir.common.dto.contact.ContactListDTO;
import com.ut.tekir.common.dto.contact.ContactSaveRequest;
import com.ut.tekir.common.entity.FinanceTxn;
import com.ut.tekir.repository.FinanceTxnRepository;
import com.ut.tekir.service.ContactService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Tag(name = "Contacts", description = "Cari hesap yönetimi")
public class ContactController {

    private final ContactService contactService;
    private final FinanceTxnRepository financeTxnRepository;

    @GetMapping
    @Operation(summary = "Cari listesi (sayfalama+filtre)")
    @PreAuthorize("hasAuthority('contact:read')")
    public Page<ContactListDTO> list(
            ContactFilterModel filter,
            @PageableDefault(size = 20, sort = "code") Pageable pageable) {
        return contactService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Cari detay")
    @PreAuthorize("hasAuthority('contact:read')")
    public ContactDetailDTO get(@PathVariable Long id) {
        return contactService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni cari oluştur")
    @PreAuthorize("hasAuthority('contact:create')")
    public ContactDetailDTO create(@Valid @RequestBody ContactSaveRequest request) {
        return contactService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cari güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactDetailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ContactSaveRequest request) {
        return contactService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cari sil")
    @PreAuthorize("hasAuthority('contact:delete')")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }

    @GetMapping("/suggest")
    @Operation(summary = "Cari arama (autocomplete)")
    @PreAuthorize("hasAuthority('contact:read')")
    public java.util.List<com.ut.tekir.common.dto.SuggestDTO> suggest(@RequestParam String q) {
        return contactService.suggest(q);
    }

    @GetMapping("/{id}/transactions")
    @Operation(summary = "Cari hareket listesi")
    @PreAuthorize("hasAuthority('contact:read')")
    public com.ut.tekir.common.dto.contact.ContactTransactionSummaryDTO listTransactions(
            @PathVariable Long id,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate) {

        java.math.BigDecimal devredenBakiyeVal = java.math.BigDecimal.ZERO;
        String currency = "TRY";

        if (startDate != null) {
            List<FinanceTxn> pastTxns = financeTxnRepository.findTransactionsBeforeDate(id, startDate);
            for (FinanceTxn txn : pastTxns) {
                if (txn.getAmount() != null && txn.getAmount().getValue() != null) {
                    if (txn.getAction() == com.ut.tekir.common.enums.FinanceAction.Debit) {
                        devredenBakiyeVal = devredenBakiyeVal.add(txn.getAmount().getValue());
                    } else if (txn.getAction() == com.ut.tekir.common.enums.FinanceAction.Credit) {
                        devredenBakiyeVal = devredenBakiyeVal.subtract(txn.getAmount().getValue());
                    }
                    if (txn.getAmount().getCurrency() != null) {
                        currency = txn.getAmount().getCurrency();
                    }
                }
            }
        }

        List<FinanceTxn> rawTxns = financeTxnRepository.findTransactionsByContactAndDateRange(id, startDate, endDate);

        java.math.BigDecimal runningBalance = devredenBakiyeVal;
        java.math.BigDecimal toplamBorcVal = java.math.BigDecimal.ZERO;
        java.math.BigDecimal toplamAlacakVal = java.math.BigDecimal.ZERO;

        List<com.ut.tekir.common.dto.contact.ContactTransactionDTO> dtoList = new java.util.ArrayList<>();

        for (FinanceTxn txn : rawTxns) {
            com.ut.tekir.common.dto.contact.ContactTransactionDTO dto = new com.ut.tekir.common.dto.contact.ContactTransactionDTO();
            dto.setId(txn.getId());
            dto.setDate(txn.getDate());
            dto.setCode(txn.getCode());

            String docCodeStr = txn.getCode() != null ? txn.getCode() : "";
            String docInfo = txn.getInfo() != null ? " - " + txn.getInfo() : "";

            if (txn.getDocumentType() != null) {
                switch (txn.getDocumentType()) {
                    case SaleInvoice:
                        dto.setDocumentClass("Satış Faturası");
                        dto.setSource("Fatura");
                        dto.setInfo(docCodeStr + " Nolu Satış Faturası" + docInfo);
                        break;
                    case PurchaseInvoice:
                        dto.setDocumentClass("Alış Faturası");
                        dto.setSource("Fatura");
                        dto.setInfo(docCodeStr + " Nolu Alış Faturası" + docInfo);
                        break;
                    case Payment:
                        dto.setDocumentClass("Ödeme/Tediye");
                        dto.setSource("Tediye");
                        dto.setInfo(docCodeStr + " Nolu Ödeme/Tediye Fişi" + docInfo);
                        break;
                    case Collection:
                        dto.setDocumentClass("Tahsilat");
                        dto.setSource("Tahsilat");
                        dto.setInfo(docCodeStr + " Nolu Tahsilat Fişi" + docInfo);
                        break;
                    case RetailSaleReturnInvoice:
                        dto.setDocumentClass("Satıştan İade");
                        dto.setSource("İade");
                        dto.setInfo(docCodeStr + " Nolu Satıştan İade Faturası" + docInfo);
                        break;
                    case DebitCreditNote:
                        dto.setDocumentClass("Borç/Alacak Dekontu");
                        dto.setSource("Dekont");
                        dto.setInfo(docCodeStr + " Nolu Dekont" + docInfo);
                        break;
                    default:
                        dto.setDocumentClass(txn.getDocumentType().name());
                        dto.setSource(txn.getDocumentType().name());
                        dto.setInfo(docCodeStr + " (" + txn.getDocumentType().name() + ")" + docInfo);
                }
            }

            java.math.BigDecimal amountVal = java.math.BigDecimal.ZERO;
            if (txn.getAmount() != null) {
                currency = txn.getAmount().getCurrency() != null ? txn.getAmount().getCurrency() : "TRY";
                amountVal = txn.getAmount().getValue() != null ? txn.getAmount().getValue() : java.math.BigDecimal.ZERO;
            }

            if (txn.getAction() == com.ut.tekir.common.enums.FinanceAction.Debit) {
                dto.setDebit(txn.getAmount());
                runningBalance = runningBalance.add(amountVal);
                toplamBorcVal = toplamBorcVal.add(amountVal);
            } else if (txn.getAction() == com.ut.tekir.common.enums.FinanceAction.Credit) {
                dto.setCredit(txn.getAmount());
                runningBalance = runningBalance.subtract(amountVal);
                toplamAlacakVal = toplamAlacakVal.add(amountVal);
            }

            com.ut.tekir.common.embeddable.MoneySet bakiyeDetails = new com.ut.tekir.common.embeddable.MoneySet();
            bakiyeDetails.setValue(runningBalance);
            bakiyeDetails.setCurrency(currency);
            bakiyeDetails.setLocalAmount(runningBalance);
            dto.setBakiye(bakiyeDetails);

            dtoList.add(dto);
        }

        java.util.Collections.reverse(dtoList);

        com.ut.tekir.common.dto.contact.ContactTransactionSummaryDTO summaryDTO = new com.ut.tekir.common.dto.contact.ContactTransactionSummaryDTO();

        com.ut.tekir.common.embeddable.MoneySet devreden = new com.ut.tekir.common.embeddable.MoneySet();
        devreden.setValue(devredenBakiyeVal);
        devreden.setCurrency(currency);
        summaryDTO.setDevredenBakiye(devreden);

        com.ut.tekir.common.embeddable.MoneySet toplamBorc = new com.ut.tekir.common.embeddable.MoneySet();
        toplamBorc.setValue(toplamBorcVal);
        toplamBorc.setCurrency(currency);
        summaryDTO.setToplamBorc(toplamBorc);

        com.ut.tekir.common.embeddable.MoneySet toplamAlacak = new com.ut.tekir.common.embeddable.MoneySet();
        toplamAlacak.setValue(toplamAlacakVal);
        toplamAlacak.setCurrency(currency);
        summaryDTO.setToplamAlacak(toplamAlacak);

        com.ut.tekir.common.embeddable.MoneySet kalan = new com.ut.tekir.common.embeddable.MoneySet();
        kalan.setValue(runningBalance);
        kalan.setCurrency(currency);
        summaryDTO.setKalanBakiye(kalan);

        summaryDTO.setTransactions(dtoList);

        return summaryDTO;
    }
}

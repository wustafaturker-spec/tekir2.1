package com.ut.tekir.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.invoice.InvoiceDetailDTO;
import com.ut.tekir.common.dto.invoice.InvoiceFilterModel;
import com.ut.tekir.common.dto.invoice.InvoiceListDTO;
import com.ut.tekir.common.dto.invoice.InvoiceSaveRequest;
import com.ut.tekir.service.InvoiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Invoice REST controller — replaces legacy saleInvoiceBrowse.seam + invoiceHome.
 * Full CRUD with pagination and filtering.
 */
@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "Fatura yönetimi")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    @Operation(summary = "Fatura listesi (sayfalama+filtre)")
    @PreAuthorize("hasAuthority('invoice:read')")
    public Page<InvoiceListDTO> list(
            InvoiceFilterModel filter,
            @PageableDefault(size = 20, sort = "date") Pageable pageable) {
        return invoiceService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fatura detay")
    @PreAuthorize("hasAuthority('invoice:read')")
    public InvoiceDetailDTO get(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni fatura oluştur")
    @PreAuthorize("hasAuthority('invoice:create')")
    public InvoiceDetailDTO create(@Valid @RequestBody InvoiceSaveRequest request) {
        return invoiceService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Fatura güncelle")
    @PreAuthorize("hasAuthority('invoice:update')")
    public InvoiceDetailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceSaveRequest request) {
        InvoiceSaveRequest withId = new InvoiceSaveRequest(
            id, request.tradeAction(), request.serial(), request.reference(),
            request.date(), request.contactId(), request.warehouseId(),
            request.accountId(), request.info(), request.items()
        );
        return invoiceService.save(withId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Fatura sil")
    @PreAuthorize("hasAuthority('invoice:delete')")
    public void delete(@PathVariable Long id) {
        invoiceService.delete(id);
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Faturayı onayla")
    @PreAuthorize("hasAuthority('invoice:update')")
    public InvoiceDetailDTO approve(@PathVariable Long id) {
        return invoiceService.approve(id);
    }
}

package com.ut.tekir.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.payment.PaymentDetailDTO;
import com.ut.tekir.common.dto.payment.PaymentFilterModel;
import com.ut.tekir.common.dto.payment.PaymentListDTO;
import com.ut.tekir.common.dto.payment.PaymentSaveRequest;
import com.ut.tekir.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Payment REST controller — replaces legacy paymentBrowse.seam + paymentHome.
 * Full CRUD with pagination and filtering for Tahsilat/Ödeme.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Tahsilat/Ödeme yönetimi")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    @Operation(summary = "Tahsilat/Ödeme listesi (sayfalama+filtre)")
    @PreAuthorize("hasAuthority('payment:read')")
    public Page<PaymentListDTO> list(
            PaymentFilterModel filter,
            @PageableDefault(size = 20, sort = "date") Pageable pageable) {
        return paymentService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Tahsilat/Ödeme detay")
    @PreAuthorize("hasAuthority('payment:read')")
    public PaymentDetailDTO get(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni tahsilat/ödeme oluştur")
    @PreAuthorize("hasAuthority('payment:create')")
    public PaymentDetailDTO create(@Valid @RequestBody PaymentSaveRequest request) {
        return paymentService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Tahsilat/Ödeme güncelle")
    @PreAuthorize("hasAuthority('payment:update')")
    public PaymentDetailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody PaymentSaveRequest request) {
        PaymentSaveRequest withId = new PaymentSaveRequest(
            id, request.paymentType(), request.date(),
            request.contactId(), request.accountId(),
            request.info(), request.items()
        );
        return paymentService.save(withId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Tahsilat/Ödeme sil")
    @PreAuthorize("hasAuthority('payment:delete')")
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }
}

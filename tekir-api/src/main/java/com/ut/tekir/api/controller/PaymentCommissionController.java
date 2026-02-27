package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.PaymentCommission;
import com.ut.tekir.service.PaymentCommissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-commissions")
@RequiredArgsConstructor
@Tag(name = "Payment Commissions", description = "Ödeme komisyonları")
public class PaymentCommissionController {

    private final PaymentCommissionService paymentCommissionService;

    @GetMapping
    @Operation(summary = "Komisyon listesi")
    @PreAuthorize("hasAuthority('payment:read')")
    public List<PaymentCommission> list() {
        return paymentCommissionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Komisyon detayı")
    @PreAuthorize("hasAuthority('payment:read')")
    public PaymentCommission get(@PathVariable Long id) {
        return paymentCommissionService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni komisyon")
    @PreAuthorize("hasAuthority('payment:create')")
    public PaymentCommission create(@Valid @RequestBody PaymentCommission commission) {
        return paymentCommissionService.save(commission);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Komisyon güncelle")
    @PreAuthorize("hasAuthority('payment:update')")
    public PaymentCommission update(@PathVariable Long id, @Valid @RequestBody PaymentCommission commission) {
        commission.setId(id);
        return paymentCommissionService.save(commission);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Komisyon sil")
    @PreAuthorize("hasAuthority('payment:delete')")
    public void delete(@PathVariable Long id) {
        paymentCommissionService.delete(id);
    }
}

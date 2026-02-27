package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.PaymentContract;
import com.ut.tekir.service.PaymentContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-contracts")
@RequiredArgsConstructor
@Tag(name = "Payment Contracts", description = "Ödeme sözleşmeleri")
public class PaymentContractController {

    private final PaymentContractService paymentContractService;

    @GetMapping
    @Operation(summary = "Ödeme sözleşmesi listesi")
    @PreAuthorize("hasAuthority('payment:read')")
    public List<PaymentContract> list() {
        return paymentContractService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ödeme sözleşmesi detayı")
    @PreAuthorize("hasAuthority('payment:read')")
    public PaymentContract get(@PathVariable Long id) {
        return paymentContractService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni ödeme sözleşmesi")
    @PreAuthorize("hasAuthority('payment:create')")
    public PaymentContract create(@Valid @RequestBody PaymentContract contract) {
        return paymentContractService.save(contract);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ödeme sözleşmesi güncelle")
    @PreAuthorize("hasAuthority('payment:update')")
    public PaymentContract update(@PathVariable Long id, @Valid @RequestBody PaymentContract contract) {
        contract.setId(id);
        return paymentContractService.save(contract);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Ödeme sözleşmesi sil")
    @PreAuthorize("hasAuthority('payment:delete')")
    public void delete(@PathVariable Long id) {
        paymentContractService.delete(id);
    }
}

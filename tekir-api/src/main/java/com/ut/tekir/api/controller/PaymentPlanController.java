package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.PaymentPlan;
import com.ut.tekir.service.PaymentPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment-plans")
@RequiredArgsConstructor
@Tag(name = "Payment Plans", description = "Ödeme planları")
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;

    @GetMapping
    @Operation(summary = "Ödeme planı listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('payment:read')")
    public Page<PaymentPlan> list(@ParameterObject Pageable pageable) {
        return paymentPlanService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ödeme planı detayı")
    @PreAuthorize("hasAuthority('payment:read')")
    public PaymentPlan get(@PathVariable Long id) {
        return paymentPlanService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni ödeme planı")
    @PreAuthorize("hasAuthority('payment:create')")
    public PaymentPlan create(@Valid @RequestBody PaymentPlan plan) {
        return paymentPlanService.save(plan);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ödeme planı güncelle")
    @PreAuthorize("hasAuthority('payment:update')")
    public PaymentPlan update(@PathVariable Long id, @Valid @RequestBody PaymentPlan plan) {
        plan.setId(id);
        return paymentPlanService.save(plan);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Ödeme planı sil")
    @PreAuthorize("hasAuthority('payment:delete')")
    public void delete(@PathVariable Long id) {
        paymentPlanService.delete(id);
    }
}

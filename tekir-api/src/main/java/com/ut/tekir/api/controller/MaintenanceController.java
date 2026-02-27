package com.ut.tekir.api.controller;

import com.ut.tekir.service.InvoiceService;
import com.ut.tekir.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/maintenance")
@RequiredArgsConstructor
@Tag(name = "Maintenance", description = "Sistem bakım işlemleri")
public class MaintenanceController {

    private final InvoiceService invoiceService;
    private final PaymentService paymentService;

    @PostMapping("/rebuild-finance-txns")
    @Operation(summary = "Eski fatura ve ödemelerin cari hareketlerini yeniden oluşturur")
    @PreAuthorize("hasAuthority('admin:maintenance')")
    public ResponseEntity<Map<String, String>> rebuildFinanceTxns() {
        invoiceService.rebuildFinanceTransactions();
        paymentService.rebuildFinanceTransactions();
        return ResponseEntity.ok(Map.of("message", "Finance transactions rebuilt successfully."));
    }
}

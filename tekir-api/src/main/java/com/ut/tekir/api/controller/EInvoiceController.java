package com.ut.tekir.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.service.einvoice.TaxPayerCheckService;
import com.ut.tekir.service.einvoice.EInvoiceService;
import com.ut.tekir.service.einvoice.dto.EInvoiceSendResult;
import com.ut.tekir.service.einvoice.dto.EInvoiceStatusResult;
import com.ut.tekir.service.einvoice.dto.GibTaxPayerInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for e-Invoice operations.
 * Phase 1: Taxpayer check only.
 * Phase 2+: Send, status check, cancel, inbound operations.
 */
@RestController
@RequestMapping("/api/v1/einvoice")
@RequiredArgsConstructor
@Tag(name = "E-Invoice", description = "e-Fatura işlemleri")
public class EInvoiceController {

    private final TaxPayerCheckService taxPayerCheckService;
    private final EInvoiceService einvoiceService;

    @GetMapping("/taxpayer/{vkn}")
    @Operation(summary = "Mükellef sorgulama (VKN ile e-Fatura kayıt kontrolü)")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<GibTaxPayerInfo> checkTaxPayer(
            @PathVariable String vkn,
            HttpServletRequest request) {
        Long tenantId = getTenantId(request);
        return ResponseEntity.ok(taxPayerCheckService.getTaxPayerInfo(vkn, String.valueOf(tenantId)));
    }

    @PostMapping("/{invoiceId}/send")
    @Operation(summary = "Faturayı e-Fatura/e-Arşiv olarak gönder")
    @PreAuthorize("hasAuthority('invoice:write')")
    public EInvoiceSendResult sendInvoice(@PathVariable Long invoiceId) {
        return einvoiceService.sendInvoice(invoiceId);
    }

    @GetMapping("/{invoiceId}/status")
    @Operation(summary = "Gönderilen faturanın durumunu sorgula")
    @PreAuthorize("hasAuthority('invoice:read')")
    public EInvoiceStatusResult checkStatus(@PathVariable Long invoiceId) {
        return einvoiceService.checkStatus(invoiceId);
    }

    @GetMapping("/{invoiceId}/pdf")
    @Operation(summary = "Faturanın PDF görüntüsünü al")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable Long invoiceId) {
        byte[] pdfData = einvoiceService.getInvoicePdf(invoiceId);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=invoice-" + invoiceId + ".pdf")
                .body(pdfData);
    }

    @PostMapping("/taxpayer/{vkn}/check")
    @Operation(summary = "Mükellef detaylı sorgulama ve cache yenileme")
    @PreAuthorize("hasAuthority('invoice:read')")
    public Map<String, Object> forceCheckTaxPayer(@PathVariable String vkn) {
        Map<String, Object> result = new HashMap<>();
        result.put("vkn", vkn);
        result.put("message", "Force check will be implemented with actual provider");
        return result;
    }

    @GetMapping("/inbound")
    @Operation(summary = "Gelen e-Faturaları listele (Entegratörden çeker)")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<java.util.List<com.ut.tekir.service.einvoice.dto.InboundInvoiceDTO>> fetchInboundInvoices(
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate,
            HttpServletRequest request) {

        Long tenantId = getTenantId(request);

        // Default to last 7 days if not provided
        if (endDate == null)
            endDate = java.time.LocalDate.now();
        if (startDate == null)
            startDate = endDate.minusDays(7);

        return ResponseEntity.ok(einvoiceService.fetchInboundInvoices(String.valueOf(tenantId), startDate, endDate));
    }

    @PostMapping("/inbound/{uuid}/respond")
    @Operation(summary = "Gelen ticari faturaya Kabul/Red yanıtı gönder")
    @PreAuthorize("hasAuthority('invoice:write')")
    public ResponseEntity<Map<String, String>> respondToInboundInvoice(
            @PathVariable String uuid,
            @RequestParam boolean accept,
            @RequestParam(required = false) String note,
            HttpServletRequest request) {

        Long tenantId = getTenantId(request);
        einvoiceService.sendResponse(String.valueOf(tenantId), uuid, accept, note);

        Map<String, String> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("message", accept ? "Fatura kabul edildi." : "Fatura reddedildi.");
        return ResponseEntity.ok(result);
    }

    private Long getTenantId(HttpServletRequest request) {
        Object tenantId = request.getAttribute("tenantId");
        if (tenantId instanceof Long) {
            return (Long) tenantId;
        }
        String header = request.getHeader("X-Tenant-Id");
        if (header != null) {
            return Long.parseLong(header);
        }
        return 1L;
    }
}

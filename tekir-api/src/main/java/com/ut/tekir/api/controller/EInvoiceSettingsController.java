package com.ut.tekir.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.service.einvoice.EInvoiceSettingsService;
import com.ut.tekir.service.einvoice.dto.EInvoiceSettingsDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for e-Invoice settings management.
 */
@RestController
@RequestMapping("/api/v1/einvoice/settings")
@RequiredArgsConstructor
@Tag(name = "E-Invoice Settings", description = "e-Fatura ayarları yönetimi")
public class EInvoiceSettingsController {

    private final EInvoiceSettingsService settingsService;

    @GetMapping
    @Operation(summary = "e-Fatura ayarlarını getir")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<EInvoiceSettings> getSettings(HttpServletRequest request) {
        Long tenantId = getTenantId(request);
        Optional<EInvoiceSettings> settings = settingsService.getSettings(String.valueOf(tenantId));
        return settings.map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PutMapping
    @Operation(summary = "e-Fatura ayarlarını kaydet")
    @PreAuthorize("hasAuthority('admin:write')")
    public EInvoiceSettings saveSettings(
            @RequestBody EInvoiceSettingsDTO dto,
            HttpServletRequest request) {
        Long tenantId = getTenantId(request);
        return settingsService.save(tenantId, dto);
    }

    @PostMapping("/test")
    @Operation(summary = "Entegratör bağlantı testi")
    @PreAuthorize("hasAuthority('admin:write')")
    public Map<String, Object> testConnection(HttpServletRequest request) {
        Long tenantId = getTenantId(request);
        boolean success = settingsService.testConnection(tenantId);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "Bağlantı başarılı" : "Bağlantı başarısız");
        return result;
    }

    private Long getTenantId(HttpServletRequest request) {
        Object tenantId = request.getAttribute("tenantId");
        if (tenantId instanceof Long) {
            return (Long) tenantId;
        }
        // Fallback: try to get from security context or header
        String header = request.getHeader("X-Tenant-Id");
        if (header != null) {
            return Long.parseLong(header);
        }
        return 1L; // Default tenant
    }
}

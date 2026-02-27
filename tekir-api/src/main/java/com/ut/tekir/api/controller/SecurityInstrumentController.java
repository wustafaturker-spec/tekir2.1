package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.Security;
import com.ut.tekir.service.SecurityInstrumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/securities")
@RequiredArgsConstructor
@Tag(name = "Securities", description = "Menkul kıymetler")
public class SecurityInstrumentController {

    private final SecurityInstrumentService securityInstrumentService;

    @GetMapping
    @Operation(summary = "Menkul kıymet listesi")
    @PreAuthorize("hasAuthority('finance:read')")
    public List<Security> list() {
        return securityInstrumentService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Menkul kıymet detayı")
    @PreAuthorize("hasAuthority('finance:read')")
    public Security get(@PathVariable Long id) {
        return securityInstrumentService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni menkul kıymet")
    @PreAuthorize("hasAuthority('finance:create')")
    public Security create(@Valid @RequestBody Security security) {
        return securityInstrumentService.save(security);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Menkul kıymet güncelle")
    @PreAuthorize("hasAuthority('finance:update')")
    public Security update(@PathVariable Long id, @Valid @RequestBody Security security) {
        security.setId(id);
        return securityInstrumentService.save(security);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Menkul kıymet sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        securityInstrumentService.delete(id);
    }
}

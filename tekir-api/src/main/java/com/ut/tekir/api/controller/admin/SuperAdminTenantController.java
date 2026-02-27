package com.ut.tekir.api.controller.admin;

import com.ut.tekir.security.JwtService;
import com.ut.tekir.tenant.dto.TenantDetailDTO;
import com.ut.tekir.tenant.dto.TenantListDTO;
import com.ut.tekir.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/tenants")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin-Tenants", description = "Kiraci yonetimi (Super Admin)")
public class SuperAdminTenantController {

    private final TenantService tenantService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @GetMapping
    @Operation(summary = "Tum kiracilari listele")
    public ResponseEntity<List<TenantListDTO>> listAll() {
        return ResponseEntity.ok(tenantService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kiraci detayi")
    public ResponseEntity<TenantDetailDTO> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getDetail(id));
    }

    @PostMapping("/{id}/suspend")
    @Operation(summary = "Kiraciyi askiya al")
    public ResponseEntity<Map<String, String>> suspend(@PathVariable Long id) {
        tenantService.suspend(id);
        return ResponseEntity.ok(Map.of("status", "suspended", "tenantId", id.toString()));
    }

    @PostMapping("/{id}/activate")
    @Operation(summary = "Kiraciyi aktiflestir")
    public ResponseEntity<Map<String, String>> activate(@PathVariable Long id) {
        tenantService.activate(id);
        return ResponseEntity.ok(Map.of("status", "activated", "tenantId", id.toString()));
    }

    @PostMapping("/{id}/impersonate")
    @Operation(summary = "Kiraci olarak giris yap (destek amacli)")
    public ResponseEntity<Map<String, String>> impersonate(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {
        // Generate a 1-hour impersonation token for the target tenant
        String impersonationToken = jwtService.generateImpersonationToken(
                currentUser, id, currentUser.getUsername());
        return ResponseEntity.ok(Map.of(
                "token", impersonationToken,
                "tenantId", id.toString(),
                "expiresIn", "3600"
        ));
    }
}



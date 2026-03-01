package com.ut.tekir.api.controller;

import com.ut.tekir.security.JwtService;
import com.ut.tekir.tenant.context.TenantContext;
import com.ut.tekir.tenant.dto.TenantRegistrationRequest;
import com.ut.tekir.tenant.dto.TenantRegistrationResponse;
import com.ut.tekir.tenant.service.TenantOnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenant Registration", description = "Kiraci kayit islemleri")
public class TenantRegistrationController {

    private final TenantOnboardingService onboardingService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    @Operation(summary = "Yeni kiraci kaydi", description = "Yeni bir sirket/kiraci olusturur ve JWT token doner")
    public ResponseEntity<TenantRegistrationResponse> register(
            @Valid @RequestBody TenantRegistrationRequest request) {

        TenantRegistrationResponse onboardResult = onboardingService.registerTenant(request);

        try {
            TenantContext.setTenantId(onboardResult.tenantId());
            var userDetails = userDetailsService.loadUserByUsername(request.adminEmail());
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new TenantRegistrationResponse(
                    onboardResult.tenantId(),
                    onboardResult.slug(),
                    accessToken,
                    refreshToken,
                    onboardResult.trialEndDate()));
        } finally {
            TenantContext.clear();
        }
    }
}

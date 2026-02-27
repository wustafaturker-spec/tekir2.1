package com.ut.tekir.api.controller;

import com.ut.tekir.security.JwtService;
import com.ut.tekir.security.TekirUserDetails;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> register(
            @Valid @RequestBody TenantRegistrationRequest request) {

        try {
            TenantRegistrationResponse onboardResult = onboardingService.registerTenant(request);

            // Generate JWT tokens for the newly created admin user
            try {
                TenantContext.setTenantId(onboardResult.tenantId());
                var userDetails = userDetailsService.loadUserByUsername(request.adminEmail());
                String accessToken = jwtService.generateToken(userDetails);
                String refreshToken = jwtService.generateRefreshToken(userDetails);

                TenantRegistrationResponse response = new TenantRegistrationResponse(
                        onboardResult.tenantId(),
                        onboardResult.slug(),
                        accessToken,
                        refreshToken,
                        onboardResult.trialEndDate());

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } finally {
                TenantContext.clear();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log to console/stderr
            java.util.Map<String, String> errorDetails = new java.util.HashMap<>();
            errorDetails.put("error", e.getMessage());
            errorDetails.put("exception", e.getClass().getName());
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            errorDetails.put("stackTrace", sw.toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }
}

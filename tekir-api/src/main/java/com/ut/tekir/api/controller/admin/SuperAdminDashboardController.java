package com.ut.tekir.api.controller.admin;

import com.ut.tekir.billing.repository.SubscriptionRepository;
import com.ut.tekir.billing.entity.SubscriptionStatus;
import com.ut.tekir.tenant.entity.TenantStatus;
import com.ut.tekir.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin-Dashboard", description = "Sistem istatistikleri (Super Admin)")
public class SuperAdminDashboardController {

    private final TenantService tenantService;
    private final SubscriptionRepository subscriptionRepository;

    @GetMapping("/stats")
    @Operation(summary = "Sistem istatistikleri")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();

        // Tenant statistics
        stats.put("totalTenants", tenantService.countAll());
        stats.put("activeTenants", tenantService.countByStatus(TenantStatus.ACTIVE));
        stats.put("trialTenants", tenantService.countByStatus(TenantStatus.TRIAL));
        stats.put("suspendedTenants", tenantService.countByStatus(TenantStatus.SUSPENDED));
        stats.put("expiredTenants", tenantService.countByStatus(TenantStatus.EXPIRED));

        // Subscription statistics
        stats.put("activeSubscriptions", subscriptionRepository.countByStatus(SubscriptionStatus.ACTIVE));
        stats.put("trialSubscriptions", subscriptionRepository.countByStatus(SubscriptionStatus.TRIAL));

        // Plan distribution
        Map<String, Long> planDistribution = new HashMap<>();
        planDistribution.put("active", subscriptionRepository.countByStatus(SubscriptionStatus.ACTIVE));
        planDistribution.put("trial", subscriptionRepository.countByStatus(SubscriptionStatus.TRIAL));
        planDistribution.put("expired", subscriptionRepository.countByStatus(SubscriptionStatus.EXPIRED));
        planDistribution.put("cancelled", subscriptionRepository.countByStatus(SubscriptionStatus.CANCELLED));
        stats.put("subscriptionDistribution", planDistribution);

        return ResponseEntity.ok(stats);
    }
}



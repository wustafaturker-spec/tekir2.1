package com.ut.tekir.api.controller.admin;

import com.ut.tekir.billing.entity.Subscription;
import com.ut.tekir.billing.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/subscriptions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin-Subscriptions", description = "Abonelik yonetimi (Super Admin)")
public class SuperAdminSubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/tenant/{tenantId}")
    @Operation(summary = "Kiraci aboneliklerini listele")
    public ResponseEntity<List<Subscription>> getByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(subscriptionService.findByTenantId(tenantId));
    }

    @PostMapping("/tenant/{tenantId}/change-plan")
    @Operation(summary = "Kiraci planini degistir")
    public ResponseEntity<Subscription> changePlan(
            @PathVariable Long tenantId,
            @RequestBody Map<String, String> request) {
        String planCode = request.get("planCode");
        return ResponseEntity.ok(subscriptionService.changePlan(tenantId, planCode));
    }

    @PostMapping("/{subscriptionId}/extend")
    @Operation(summary = "Abonelik suresini uzat")
    public ResponseEntity<Subscription> extend(
            @PathVariable Long subscriptionId,
            @RequestBody Map<String, Integer> request) {
        int days = request.getOrDefault("days", 30);
        return ResponseEntity.ok(subscriptionService.extendSubscription(subscriptionId, days));
    }

    @PostMapping("/tenant/{tenantId}/cancel")
    @Operation(summary = "Kiraci aboneligini iptal et")
    public ResponseEntity<Map<String, String>> cancel(@PathVariable Long tenantId) {
        subscriptionService.cancelSubscription(tenantId);
        return ResponseEntity.ok(Map.of("status", "cancelled", "tenantId", tenantId.toString()));
    }
}



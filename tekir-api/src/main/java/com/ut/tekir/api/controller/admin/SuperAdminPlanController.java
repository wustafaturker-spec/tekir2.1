package com.ut.tekir.api.controller.admin;

import com.ut.tekir.billing.entity.Plan;
import com.ut.tekir.billing.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/plans")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Tag(name = "Super Admin-Plans", description = "Plan yonetimi (Super Admin)")
public class SuperAdminPlanController {

    private final PlanService planService;

    @GetMapping
    @Operation(summary = "Tum planlari listele")
    public ResponseEntity<List<Plan>> listAll() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Plan detayi")
    public ResponseEntity<Plan> getById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Yeni plan olustur")
    public ResponseEntity<Plan> create(@RequestBody Plan plan) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.create(plan));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Plan guncelle")
    public ResponseEntity<Plan> update(@PathVariable Long id, @RequestBody Plan plan) {
        return ResponseEntity.ok(planService.update(id, plan));
    }
}



package com.ut.tekir.accounting.controller;

import com.ut.tekir.accounting.dto.ChartAccountDTO;
import com.ut.tekir.accounting.dto.ChartAccountSaveRequest;
import com.ut.tekir.accounting.dto.ChartAccountTreeDTO;
import com.ut.tekir.accounting.service.ChartAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounting/plans/{planId}/accounts")
@RequiredArgsConstructor
public class ChartAccountController {

    private final ChartAccountService service;

    @GetMapping
    @PreAuthorize("hasAuthority('accounting:read')")
    public ResponseEntity<?> getAccounts(@PathVariable Long planId,
                                          @RequestParam(defaultValue = "false") boolean flat) {
        if (flat) {
            return ResponseEntity.ok(service.getFlat(planId));
        }
        return ResponseEntity.ok(service.getTree(planId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('accounting:read')")
    public ResponseEntity<ChartAccountDTO> getById(@PathVariable Long planId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(planId, id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('accounting:create')")
    public ResponseEntity<ChartAccountDTO> create(@PathVariable Long planId,
                                                   @Valid @RequestBody ChartAccountSaveRequest request) {
        return ResponseEntity.ok(service.create(planId, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('accounting:update')")
    public ResponseEntity<ChartAccountDTO> update(@PathVariable Long planId,
                                                   @PathVariable Long id,
                                                   @Valid @RequestBody ChartAccountSaveRequest request) {
        return ResponseEntity.ok(service.update(planId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('accounting:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long planId, @PathVariable Long id) {
        service.delete(planId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/suggest")
    @PreAuthorize("hasAuthority('accounting:read')")
    public ResponseEntity<List<ChartAccountDTO>> suggest(@PathVariable Long planId,
                                                          @RequestParam(defaultValue = "") String q) {
        return ResponseEntity.ok(service.suggest(planId, q));
    }
}

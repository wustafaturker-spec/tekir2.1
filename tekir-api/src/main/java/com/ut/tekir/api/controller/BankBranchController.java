package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.BankBranch;
import com.ut.tekir.service.BankBranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-branches")
@RequiredArgsConstructor
@Tag(name = "Bank Branches", description = "Banka şubeleri")
public class BankBranchController {

    private final BankBranchService bankBranchService;

    @GetMapping
    @Operation(summary = "Banka şubesi listesi")
    @PreAuthorize("hasAuthority('bank:read')")
    public List<BankBranch> list(@RequestParam(required = false) Long bankId) {
        if (bankId != null) {
            return bankBranchService.findByBankId(bankId);
        }
        return bankBranchService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Banka şubesi detayı")
    @PreAuthorize("hasAuthority('bank:read')")
    public BankBranch get(@PathVariable Long id) {
        return bankBranchService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni banka şubesi")
    @PreAuthorize("hasAuthority('bank:create')")
    public BankBranch create(@Valid @RequestBody BankBranch branch) {
        return bankBranchService.save(branch);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Banka şubesi güncelle")
    @PreAuthorize("hasAuthority('bank:update')")
    public BankBranch update(@PathVariable Long id, @Valid @RequestBody BankBranch branch) {
        branch.setId(id);
        return bankBranchService.save(branch);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Banka şubesi sil")
    @PreAuthorize("hasAuthority('bank:delete')")
    public void delete(@PathVariable Long id) {
        bankBranchService.delete(id);
    }
}

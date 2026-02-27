package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-accounts")
@RequiredArgsConstructor
@Tag(name = "Bank Accounts", description = "Banka hesapları")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    @Operation(summary = "Banka hesabı listesi")
    @PreAuthorize("hasAuthority('bank:read')")
    public List<BankAccount> list(@RequestParam(required = false) Long branchId) {
        if (branchId != null) {
            return bankAccountService.findByBranchId(branchId);
        }
        return bankAccountService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Banka hesabı detayı")
    @PreAuthorize("hasAuthority('bank:read')")
    public BankAccount get(@PathVariable Long id) {
        return bankAccountService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni banka hesabı")
    @PreAuthorize("hasAuthority('bank:create')")
    public BankAccount create(@Valid @RequestBody BankAccount account) {
        return bankAccountService.save(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Banka hesabı güncelle")
    @PreAuthorize("hasAuthority('bank:update')")
    public BankAccount update(@PathVariable Long id, @Valid @RequestBody BankAccount account) {
        account.setId(id);
        return bankAccountService.save(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Banka hesabı sil")
    @PreAuthorize("hasAuthority('bank:delete')")
    public void delete(@PathVariable Long id) {
        bankAccountService.delete(id);
    }
}

package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.DepositAccount;
import com.ut.tekir.service.DepositAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deposit-accounts")
@RequiredArgsConstructor
@Tag(name = "Deposit Accounts", description = "Vadeli hesap yönetimi")
public class DepositAccountController {

    private final DepositAccountService depositAccountService;

    @GetMapping
    @Operation(summary = "Vadeli hesap listesi")
    public Page<DepositAccount> list(Pageable pageable) {
        return depositAccountService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Vadeli hesap detayı")
    public DepositAccount getById(@PathVariable Long id) {
        return depositAccountService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Vadeli hesap oluştur")
    @PreAuthorize("hasAuthority('finance:create')")
    public DepositAccount create(@RequestBody DepositAccount account) {
        return depositAccountService.save(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Vadeli hesap güncelle")
    @PreAuthorize("hasAuthority('finance:update')")
    public DepositAccount update(@PathVariable Long id, @RequestBody DepositAccount account) {
        account.setId(id);
        return depositAccountService.save(account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Vadeli hesap sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        depositAccountService.delete(id);
    }
}

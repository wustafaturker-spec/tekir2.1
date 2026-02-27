package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.account.AccountDetailDTO;
import com.ut.tekir.common.dto.account.AccountFilterModel;
import com.ut.tekir.common.dto.account.AccountListDTO;
import com.ut.tekir.common.dto.account.AccountSaveRequest;
import com.ut.tekir.common.entity.AccountTxn;
import com.ut.tekir.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Kasa/Hesap yönetimi")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "Hesap listesi (Sayfalı/Filtreli)")
    @PreAuthorize("hasAuthority('account:read')")
    public Page<AccountListDTO> search(@ParameterObject @ModelAttribute AccountFilterModel filter, 
                                      @ParameterObject Pageable pageable) {
        return accountService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Hesap detayı")
    @PreAuthorize("hasAuthority('account:read')")
    public AccountDetailDTO get(@PathVariable Long id) {
        return accountService.get(id);
    }

    @PostMapping
    @Operation(summary = "Yeni hesap")
    @PreAuthorize("hasAuthority('account:create')")
    public AccountDetailDTO create(@Valid @RequestBody AccountSaveRequest request) {
        return accountService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Hesap güncelle")
    @PreAuthorize("hasAuthority('account:update')")
    public AccountDetailDTO update(@PathVariable Long id, @Valid @RequestBody AccountSaveRequest request) {
        return accountService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hesap sil")
    @PreAuthorize("hasAuthority('account:delete')")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @GetMapping("/suggest")
    @Operation(summary = "Hesap arama (autocomplete)")
    @PreAuthorize("hasAuthority('account:read')")
    public java.util.List<com.ut.tekir.common.dto.SuggestDTO> suggest(@RequestParam String q) {
        return accountService.suggest(q);
    }

    @GetMapping("/{id}/transactions")
    @Operation(summary = "Hesap hareketleri")
    @PreAuthorize("hasAuthority('account:read')")
    public List<AccountTxn> listTransactions(@PathVariable Long id) {
        return accountService.getTransactions(id);
    }
}




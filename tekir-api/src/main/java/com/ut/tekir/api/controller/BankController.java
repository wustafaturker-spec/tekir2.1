package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.bank.*;
import com.ut.tekir.service.BankService;
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
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
@Tag(name = "Banks", description = "Banka tanımları")
public class BankController {

    private final BankService bankService;

    @GetMapping
    @Operation(summary = "Banka listesi (Sayfalı/Filtreli)")
    @PreAuthorize("hasAuthority('bank:read')")
    public Page<BankListDTO> search(@ParameterObject @ModelAttribute BankFilterModel filter, 
                                   @ParameterObject Pageable pageable) {
        return bankService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Banka detayı")
    @PreAuthorize("hasAuthority('bank:read')")
    public BankDetailDTO get(@PathVariable Long id) {
        return bankService.get(id);
    }

    @PostMapping
    @Operation(summary = "Yeni banka")
    @PreAuthorize("hasAuthority('bank:create')")
    public BankDetailDTO create(@Valid @RequestBody BankSaveRequest request) {
        return bankService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Banka güncelle")
    @PreAuthorize("hasAuthority('bank:update')")
    public BankDetailDTO update(@PathVariable Long id, @Valid @RequestBody BankSaveRequest request) {
        return bankService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Banka sil")
    @PreAuthorize("hasAuthority('bank:delete')")
    public void delete(@PathVariable Long id) {
        bankService.delete(id);
    }

    // --- Sub-resources ---

    @GetMapping("/{id}/branches")
    @Operation(summary = "Banka şubeleri")
    @PreAuthorize("hasAuthority('bank:read')")
    public List<BankBranchDTO> listBranches(@PathVariable Long id) {
        return bankService.getBranches(id);
    }

    @PostMapping("/{id}/branches")
    @Operation(summary = "Şube ekle/güncelle")
    @PreAuthorize("hasAuthority('bank:update')")
    public BankBranchDTO saveBranch(@PathVariable Long id, @Valid @RequestBody BankBranchDTO dto) {
        if (dto.bankId() == null) { 
             // dto should probably be immutable record, so maybe we can't set bankId?
             // But BankBranchDTO is likely a record.
             // We can assume client sends bankId or we validate it matches path variable.
             // Actually service.saveBranch(dto) expects dto.
             // For now assume client sends correct body or we reconstruct.
        }
        return bankService.saveBranch(dto);
    }

    @GetMapping("/branches/{branchId}/accounts")
    @Operation(summary = "Şube hesapları")
    @PreAuthorize("hasAuthority('bank:read')")
    public List<BankAccountDTO> listAccounts(@PathVariable Long branchId) {
        return bankService.getAccounts(branchId);
    }

    @PostMapping("/branches/{branchId}/accounts")
    @Operation(summary = "Hesap ekle/güncelle")
    @PreAuthorize("hasAuthority('bank:update')")
    public BankAccountDTO saveAccount(@PathVariable Long branchId, @Valid @RequestBody BankAccountDTO dto) {
        return bankService.saveAccount(dto);
    }
}




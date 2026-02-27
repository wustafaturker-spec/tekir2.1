package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.cheque.*;
import com.ut.tekir.common.dto.promissory.*;
import com.ut.tekir.common.enums.ChequeStatus;
import com.ut.tekir.service.ChequeService;
import com.ut.tekir.service.PromissoryNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cheque-promissory")
@RequiredArgsConstructor
@Tag(name = "Cheque & Promissory", description = "Çek/Senet yönetimi")
public class ChequePromissoryController {

    private final ChequeService chequeService;
    private final PromissoryNoteService promissoryNoteService;

    // --- Cheques ---

    @GetMapping("/cheques")
    @Operation(summary = "Çek listesi (Sayfalı/Filtreli)")
    @PreAuthorize("hasAuthority('cheque:read')")
    public Page<ChequeListDTO> searchCheques(@ParameterObject @ModelAttribute ChequeFilterModel filter,
            @ParameterObject Pageable pageable) {
        return chequeService.search(filter, pageable);
    }

    @GetMapping("/cheques/{id}")
    @Operation(summary = "Çek detayı")
    @PreAuthorize("hasAuthority('cheque:read')")
    public ChequeDetailDTO getCheque(@PathVariable Long id) {
        return chequeService.get(id);
    }

    @PostMapping("/cheques")
    @Operation(summary = "Çek kaydet")
    @PreAuthorize("hasAuthority('cheque:create')")
    public ChequeDetailDTO createCheque(@Valid @RequestBody ChequeSaveRequest request) {
        return chequeService.create(request);
    }

    @PutMapping("/cheques/{id}")
    @Operation(summary = "Çek güncelle")
    @PreAuthorize("hasAuthority('cheque:update')")
    public ChequeDetailDTO updateCheque(@PathVariable Long id, @Valid @RequestBody ChequeSaveRequest request) {
        return chequeService.update(id, request);
    }

    @DeleteMapping("/cheques/{id}")
    @Operation(summary = "Çek sil")
    @PreAuthorize("hasAuthority('cheque:delete')")
    public void deleteCheque(@PathVariable Long id) {
        chequeService.delete(id);
    }

    @PutMapping("/cheques/{id}/status")
    @Operation(summary = "Çek durum değiştir")
    @PreAuthorize("hasAuthority('cheque:update')")
    public ChequeDetailDTO changeChequeStatus(@PathVariable Long id, @RequestParam ChequeStatus status) {
        return chequeService.changeStatus(id, status);
    }

    @PutMapping("/cheques/{id}/endorse")
    @Operation(summary = "Çek ciro et")
    @PreAuthorize("hasAuthority('cheque:update')")
    public ChequeDetailDTO endorseCheque(@PathVariable Long id, @RequestParam Long targetContactId) {
        return chequeService.endorse(id, targetContactId);
    }

    @PostMapping("/cheques/payroll")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @Operation(summary = "Çek bordrosu oluştur")
    @PreAuthorize("hasAuthority('cheque:create')")
    public ChequePayrollDetailDTO createChequePayroll(@Valid @RequestBody ChequePayrollSaveRequest request) {
        return chequeService.savePayroll(request);
    }

    // --- Promissory Notes ---

    @GetMapping("/promissory-notes")
    @Operation(summary = "Senet listesi (Sayfalı/Filtreli)")
    @PreAuthorize("hasAuthority('promissory:read')")
    public Page<PromissoryNoteListDTO> searchPromissory(
            @ParameterObject @ModelAttribute PromissoryNoteFilterModel filter,
            @ParameterObject Pageable pageable) {
        return promissoryNoteService.search(filter, pageable);
    }

    @GetMapping("/promissory-notes/{id}")
    @Operation(summary = "Senet detayı")
    @PreAuthorize("hasAuthority('promissory:read')")
    public PromissoryNoteDetailDTO getPromissory(@PathVariable Long id) {
        return promissoryNoteService.get(id);
    }

    @PostMapping("/promissory-notes")
    @Operation(summary = "Senet kaydet")
    @PreAuthorize("hasAuthority('promissory:create')")
    public PromissoryNoteDetailDTO createPromissory(@Valid @RequestBody PromissoryNoteSaveRequest request) {
        return promissoryNoteService.create(request);
    }

    @PutMapping("/promissory-notes/{id}")
    @Operation(summary = "Senet güncelle")
    @PreAuthorize("hasAuthority('promissory:update')")
    public PromissoryNoteDetailDTO updatePromissory(@PathVariable Long id,
            @Valid @RequestBody PromissoryNoteSaveRequest request) {
        return promissoryNoteService.update(id, request);
    }

    @DeleteMapping("/promissory-notes/{id}")
    @Operation(summary = "Senet sil")
    @PreAuthorize("hasAuthority('promissory:delete')")
    public void deletePromissory(@PathVariable Long id) {
        promissoryNoteService.delete(id);
    }

    @PutMapping("/promissory-notes/{id}/status")
    @Operation(summary = "Senet durum değiştir")
    @PreAuthorize("hasAuthority('promissory:update')")
    public PromissoryNoteDetailDTO changePromissoryStatus(@PathVariable Long id, @RequestParam ChequeStatus status) {
        return promissoryNoteService.changeStatus(id, status);
    }

    @PutMapping("/promissory-notes/{id}/endorse")
    @Operation(summary = "Senet ciro et")
    @PreAuthorize("hasAuthority('promissory:update')")
    public PromissoryNoteDetailDTO endorsePromissory(@PathVariable Long id, @RequestParam Long targetContactId) {
        return promissoryNoteService.endorse(id, targetContactId);
    }
}

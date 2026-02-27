package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.DebitCreditNote;
import com.ut.tekir.common.entity.DebitCreditVirement;
import com.ut.tekir.service.DebitCreditNoteService;
import com.ut.tekir.service.DebitCreditVirementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/debit-credit")
@RequiredArgsConstructor
@Tag(name = "Debit/Credit", description = "Borç/Alacak dekont ve virman yönetimi")
public class DebitCreditController {

    private final DebitCreditNoteService noteService;
    private final DebitCreditVirementService virementService;

    // --- Debit/Credit Notes ---
    @GetMapping("/notes")
    @Operation(summary = "Borç/Alacak dekont listesi")
    public Page<DebitCreditNote> listNotes(Pageable pageable) {
        return noteService.findAll(pageable);
    }

    @GetMapping("/notes/{id}")
    @Operation(summary = "Borç/Alacak dekont detayı")
    public DebitCreditNote getNote(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Borç/Alacak dekont oluştur")
    @PreAuthorize("hasAuthority('finance:create')")
    public DebitCreditNote createNote(@RequestBody DebitCreditNote note) {
        return noteService.save(note);
    }

    @DeleteMapping("/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Borç/Alacak dekont sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void deleteNote(@PathVariable Long id) {
        noteService.delete(id);
    }

    // --- Virements ---
    @GetMapping("/virements")
    @Operation(summary = "Borç/Alacak virman listesi")
    public Page<DebitCreditVirement> listVirements(Pageable pageable) {
        return virementService.findAll(pageable);
    }

    @GetMapping("/virements/{id}")
    @Operation(summary = "Borç/Alacak virman detayı")
    public DebitCreditVirement getVirement(@PathVariable Long id) {
        return virementService.findById(id);
    }

    @PostMapping("/virements")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Borç/Alacak virman oluştur")
    @PreAuthorize("hasAuthority('finance:create')")
    public DebitCreditVirement createVirement(@RequestBody DebitCreditVirement virement) {
        return virementService.save(virement);
    }

    @DeleteMapping("/virements/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Borç/Alacak virman sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void deleteVirement(@PathVariable Long id) {
        virementService.delete(id);
    }
}

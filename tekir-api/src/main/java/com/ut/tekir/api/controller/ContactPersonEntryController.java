package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.contact.ContactPersonEntryDTO;
import com.ut.tekir.common.dto.contact.ContactPersonEntrySaveRequest;
import com.ut.tekir.service.ContactPersonEntryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts/{contactId}/persons")
@RequiredArgsConstructor
@Tag(name = "ContactPersons", description = "Cari yetkili kişi yönetimi")
public class ContactPersonEntryController {

    private final ContactPersonEntryService personEntryService;

    @GetMapping
    @Operation(summary = "Yetkili kişi listesi")
    @PreAuthorize("hasAuthority('contact:read')")
    public List<ContactPersonEntryDTO> list(@PathVariable Long contactId) {
        return personEntryService.findByContactId(contactId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yetkili kişi ekle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactPersonEntryDTO create(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactPersonEntrySaveRequest request) {
        return personEntryService.save(new ContactPersonEntrySaveRequest(
            null, contactId,
            request.firstName(), request.lastName(), request.jobTitle(), request.department(),
            request.email(), request.phone(), request.mobile(), request.isDefault(), request.note()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Yetkili kişi güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactPersonEntryDTO update(
            @PathVariable Long contactId,
            @PathVariable Long id,
            @Valid @RequestBody ContactPersonEntrySaveRequest request) {
        return personEntryService.save(new ContactPersonEntrySaveRequest(
            id, contactId,
            request.firstName(), request.lastName(), request.jobTitle(), request.department(),
            request.email(), request.phone(), request.mobile(), request.isDefault(), request.note()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Yetkili kişi sil")
    @PreAuthorize("hasAuthority('contact:update')")
    public void delete(@PathVariable Long contactId, @PathVariable Long id) {
        personEntryService.delete(id);
    }
}

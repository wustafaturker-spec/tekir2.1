package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.contact.ContactPhoneDTO;
import com.ut.tekir.common.dto.contact.ContactPhoneSaveRequest;
import com.ut.tekir.service.ContactPhoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts/{contactId}/phones")
@RequiredArgsConstructor
@Tag(name = "ContactPhones", description = "Cari telefon yönetimi")
public class ContactPhoneController {

    private final ContactPhoneService phoneService;

    @GetMapping
    @Operation(summary = "Telefon listesi")
    @PreAuthorize("hasAuthority('contact:read')")
    public List<ContactPhoneDTO> list(@PathVariable Long contactId) {
        return phoneService.findByContactId(contactId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Telefon ekle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactPhoneDTO create(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactPhoneSaveRequest request) {
        return phoneService.save(contactId, new ContactPhoneSaveRequest(
            null,
            request.info(), request.countryCode(), request.areaCode(),
            request.phoneNumber(), request.extension(),
            request.activePhone(), request.defaultPhone(),
            request.homePhone(), request.workPhone(),
            request.gsmPhone(), request.faxPhone()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Telefon güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactPhoneDTO update(
            @PathVariable Long contactId,
            @PathVariable Long id,
            @Valid @RequestBody ContactPhoneSaveRequest request) {
        return phoneService.save(contactId, new ContactPhoneSaveRequest(
            id,
            request.info(), request.countryCode(), request.areaCode(),
            request.phoneNumber(), request.extension(),
            request.activePhone(), request.defaultPhone(),
            request.homePhone(), request.workPhone(),
            request.gsmPhone(), request.faxPhone()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Telefon sil")
    @PreAuthorize("hasAuthority('contact:update')")
    public void delete(@PathVariable Long contactId, @PathVariable Long id) {
        phoneService.delete(id);
    }
}

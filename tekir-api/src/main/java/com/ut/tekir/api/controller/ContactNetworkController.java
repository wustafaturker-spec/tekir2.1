package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.contact.ContactNetworkDTO;
import com.ut.tekir.common.dto.contact.ContactNetworkSaveRequest;
import com.ut.tekir.service.ContactNetworkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts/{contactId}/networks")
@RequiredArgsConstructor
@Tag(name = "ContactNetworks", description = "Cari dijital adres / iletişim kanalı yönetimi")
public class ContactNetworkController {

    private final ContactNetworkService networkService;

    @GetMapping
    @Operation(summary = "Ağ/iletişim kanalı listesi")
    @PreAuthorize("hasAuthority('contact:read')")
    public List<ContactNetworkDTO> list(@PathVariable Long contactId) {
        return networkService.findByContactId(contactId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ağ/iletişim kanalı ekle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactNetworkDTO create(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactNetworkSaveRequest request) {
        return networkService.save(contactId, new ContactNetworkSaveRequest(
            null, contactId,
            request.networkType(), request.value(),
            request.defaultNetwork(), request.activeNetwork(), request.info()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ağ/iletişim kanalı güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactNetworkDTO update(
            @PathVariable Long contactId,
            @PathVariable Long id,
            @Valid @RequestBody ContactNetworkSaveRequest request) {
        return networkService.save(contactId, new ContactNetworkSaveRequest(
            id, contactId,
            request.networkType(), request.value(),
            request.defaultNetwork(), request.activeNetwork(), request.info()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ağ/iletişim kanalı sil")
    @PreAuthorize("hasAuthority('contact:update')")
    public void delete(@PathVariable Long contactId, @PathVariable Long id) {
        networkService.delete(id);
    }
}

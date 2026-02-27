package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.contact.ContactAddressDTO;
import com.ut.tekir.common.dto.contact.ContactAddressSaveRequest;
import com.ut.tekir.service.ContactAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts/{contactId}/addresses")
@RequiredArgsConstructor
@Tag(name = "ContactAddresses", description = "Cari adres yönetimi")
public class ContactAddressController {

    private final ContactAddressService addressService;

    @GetMapping
    @Operation(summary = "Adres listesi")
    @PreAuthorize("hasAuthority('contact:read')")
    public List<ContactAddressDTO> list(@PathVariable Long contactId) {
        return addressService.findByContactId(contactId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adres ekle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactAddressDTO create(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactAddressSaveRequest request) {
        return addressService.save(contactId, new ContactAddressSaveRequest(
            null,
            request.street(), request.city(), request.province(),
            request.country(), request.zip(),
            request.invoiceAddress(), request.shippingAddress(),
            request.homeAddress(), request.workAddress(), request.otherAddress(),
            request.defaultAddress(), request.activeAddress(),
            request.recipientName(), request.recipientSurname(), request.info()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Adres güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactAddressDTO update(
            @PathVariable Long contactId,
            @PathVariable Long id,
            @Valid @RequestBody ContactAddressSaveRequest request) {
        return addressService.save(contactId, new ContactAddressSaveRequest(
            id,
            request.street(), request.city(), request.province(),
            request.country(), request.zip(),
            request.invoiceAddress(), request.shippingAddress(),
            request.homeAddress(), request.workAddress(), request.otherAddress(),
            request.defaultAddress(), request.activeAddress(),
            request.recipientName(), request.recipientSurname(), request.info()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Adres sil")
    @PreAuthorize("hasAuthority('contact:update')")
    public void delete(@PathVariable Long contactId, @PathVariable Long id) {
        addressService.delete(id);
    }
}

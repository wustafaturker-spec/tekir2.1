package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.contact.ContactOpportunityDTO;
import com.ut.tekir.common.dto.contact.ContactOpportunitySaveRequest;
import com.ut.tekir.service.ContactOpportunityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts/{contactId}/opportunities")
@RequiredArgsConstructor
@Tag(name = "ContactOpportunities", description = "Cari satış fırsatları / pipeline yönetimi")
public class ContactOpportunityController {

    private final ContactOpportunityService opportunityService;

    @GetMapping
    @Operation(summary = "Fırsat listesi")
    @PreAuthorize("hasAuthority('contact:read')")
    public List<ContactOpportunityDTO> list(@PathVariable Long contactId) {
        return opportunityService.findByContactId(contactId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fırsat detay")
    @PreAuthorize("hasAuthority('contact:read')")
    public ContactOpportunityDTO get(@PathVariable Long contactId, @PathVariable Long id) {
        return opportunityService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni fırsat oluştur")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactOpportunityDTO create(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactOpportunitySaveRequest request) {
        return opportunityService.save(new ContactOpportunitySaveRequest(
            null, contactId, request.title(), request.stage(),
            request.expectedRevenue(), request.currency(), request.probability(),
            request.expectedCloseDate(), request.actualCloseDate(),
            request.note(), request.assignedTo(), request.active()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Fırsat güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactOpportunityDTO update(
            @PathVariable Long contactId,
            @PathVariable Long id,
            @Valid @RequestBody ContactOpportunitySaveRequest request) {
        return opportunityService.save(new ContactOpportunitySaveRequest(
            id, contactId, request.title(), request.stage(),
            request.expectedRevenue(), request.currency(), request.probability(),
            request.expectedCloseDate(), request.actualCloseDate(),
            request.note(), request.assignedTo(), request.active()
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Fırsat sil")
    @PreAuthorize("hasAuthority('contact:update')")
    public void delete(@PathVariable Long contactId, @PathVariable Long id) {
        opportunityService.delete(id);
    }
}

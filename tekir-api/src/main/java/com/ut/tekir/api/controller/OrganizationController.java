package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.entity.Organization;
import com.ut.tekir.service.OrganizationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations", description = "Organizasyon yönetimi")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    @Operation(summary = "Organizasyon listesi")
    public List<Organization> list() {
        return organizationService.findAll();
    }

    @GetMapping("/roots")
    @Operation(summary = "Kök organizasyonlar (üst organizasyonu olmayanlar)")
    public List<Organization> roots() {
        return organizationService.findRoots();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Organizasyon detay")
    public Organization get(@PathVariable Long id) {
        return organizationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni organizasyon")
    public Organization create(@RequestBody Organization organization) {
        return organizationService.save(organization);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Organizasyon güncelle")
    public Organization update(@PathVariable Long id, @RequestBody Organization organization) {
        organization.setId(id);
        return organizationService.save(organization);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Organizasyon sil")
    public void delete(@PathVariable Long id) {
        organizationService.delete(id);
    }
}



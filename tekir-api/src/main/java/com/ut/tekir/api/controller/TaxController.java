package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.entity.Tax;
import com.ut.tekir.service.TaxService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/taxes")
@RequiredArgsConstructor
@Tag(name = "Taxes", description = "Vergi yönetimi")
public class TaxController {

    private final TaxService taxService;

    @GetMapping
    @Operation(summary = "Vergi listesi")
    public List<Tax> list() {
        return taxService.findAllActive();
    }

    @GetMapping("/all")
    @Operation(summary = "Tüm vergiler (pasifler dahil)")
    public List<Tax> listAll() {
        return taxService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Vergi detay")
    public Tax get(@PathVariable Long id) {
        return taxService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni vergi")
    public Tax create(@RequestBody Tax tax) {
        return taxService.save(tax);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Vergi güncelle")
    public Tax update(@PathVariable Long id, @RequestBody Tax tax) {
        tax.setId(id);
        return taxService.save(tax);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Vergi sil")
    public void delete(@PathVariable Long id) {
        taxService.delete(id);
    }
}



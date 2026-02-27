package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.entity.Unit;
import com.ut.tekir.service.UnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
@Tag(name = "Units", description = "Ölçü birimi yönetimi")
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    @Operation(summary = "Birim listesi")
    public List<Unit> list() {
        return unitService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Birim detay")
    public Unit get(@PathVariable Long id) {
        return unitService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni birim")
    public Unit create(@RequestBody Unit unit) {
        return unitService.save(unit);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Birim güncelle")
    public Unit update(@PathVariable Long id, @RequestBody Unit unit) {
        unit.setId(id);
        return unitService.save(unit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Birim sil")
    public void delete(@PathVariable Long id) {
        unitService.delete(id);
    }
}



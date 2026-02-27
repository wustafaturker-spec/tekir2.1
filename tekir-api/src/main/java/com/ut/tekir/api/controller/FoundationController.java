package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.entity.Foundation;
import com.ut.tekir.service.FoundationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/foundations")
@RequiredArgsConstructor
@Tag(name = "Foundations", description = "Kurum yönetimi")
public class FoundationController {

    private final FoundationService foundationService;

    @GetMapping
    @Operation(summary = "Kurum listesi")
    public List<Foundation> list() {
        return foundationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kurum detay")
    public Foundation get(@PathVariable Long id) {
        return foundationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni kurum")
    public Foundation create(@RequestBody Foundation foundation) {
        return foundationService.save(foundation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kurum güncelle")
    public Foundation update(@PathVariable Long id, @RequestBody Foundation foundation) {
        foundation.setId(id);
        return foundationService.save(foundation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kurum sil")
    public void delete(@PathVariable Long id) {
        foundationService.delete(id);
    }
}



package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.CurrencyPair;
import com.ut.tekir.service.CurrencyPairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currency-pairs")
@RequiredArgsConstructor
@Tag(name = "Currency Pairs", description = "Döviz çiftleri")
public class CurrencyPairController {

    private final CurrencyPairService currencyPairService;

    @GetMapping
    @Operation(summary = "Döviz çifti listesi")
    @PreAuthorize("hasAuthority('currency:read')")
    public List<CurrencyPair> list() {
        return currencyPairService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Döviz çifti detayı")
    @PreAuthorize("hasAuthority('currency:read')")
    public CurrencyPair get(@PathVariable Long id) {
        return currencyPairService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni döviz çifti")
    @PreAuthorize("hasAuthority('currency:create')")
    public CurrencyPair create(@Valid @RequestBody CurrencyPair pair) {
        return currencyPairService.save(pair);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Döviz çifti sil")
    @PreAuthorize("hasAuthority('currency:delete')")
    public void delete(@PathVariable Long id) {
        currencyPairService.delete(id);
    }
}

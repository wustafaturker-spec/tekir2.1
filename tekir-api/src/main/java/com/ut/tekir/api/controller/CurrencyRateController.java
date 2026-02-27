package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.CurrencyRate;
import com.ut.tekir.service.CurrencyRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currency-rates")
@RequiredArgsConstructor
@Tag(name = "Currency Rates", description = "Döviz kurları")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @GetMapping
    @Operation(summary = "Döviz kuru listesi")
    @PreAuthorize("hasAuthority('currency:read')")
    public List<CurrencyRate> list() {
        return currencyRateService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Döviz kuru detayı")
    @PreAuthorize("hasAuthority('currency:read')")
    public CurrencyRate get(@PathVariable Long id) {
        return currencyRateService.getById(id);
    }

    @GetMapping("/by-pair")
    @Operation(summary = "Çift ve tarihe göre döviz kuru")
    @PreAuthorize("hasAuthority('currency:read')")
    public List<CurrencyRate> findByPairAndDate(@RequestParam Long pairId, @RequestParam LocalDate date) {
        return currencyRateService.findByPairAndDate(pairId, date);
    }

    @GetMapping("/latest")
    @Operation(summary = "Çift için son döviz kuru")
    @PreAuthorize("hasAuthority('currency:read')")
    public CurrencyRate findLatest(@RequestParam Long pairId) {
        return currencyRateService.findLatest(pairId).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Yeni döviz kuru")
    @PreAuthorize("hasAuthority('currency:create')")
    public CurrencyRate create(@Valid @RequestBody CurrencyRate rate) {
        return currencyRateService.save(rate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Döviz kuru sil")
    @PreAuthorize("hasAuthority('currency:delete')")
    public void delete(@PathVariable Long id) {
        currencyRateService.delete(id);
    }
}

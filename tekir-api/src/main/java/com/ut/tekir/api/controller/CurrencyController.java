package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.Currency;
import com.ut.tekir.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
@Tag(name = "Currencies", description = "Döviz tanımları")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    @Operation(summary = "Döviz listesi")
    public List<Currency> list() {
        return currencyService.findAll();
    }
    
    @GetMapping("/active")
    @Operation(summary = "Aktif döviz listesi")
    public List<Currency> listActive() {
        return currencyService.findAllActive();
    }
}



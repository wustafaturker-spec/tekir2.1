package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.ForeignExchange;
import com.ut.tekir.service.ForeignExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/foreign-exchanges")
@RequiredArgsConstructor
@Tag(name = "Foreign Exchanges", description = "Döviz alım/satım yönetimi")
public class ForeignExchangeController {

    private final ForeignExchangeService foreignExchangeService;

    @GetMapping
    @Operation(summary = "Döviz alım/satım listesi")
    public Page<ForeignExchange> list(Pageable pageable) {
        return foreignExchangeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Döviz işlem detayı")
    public ForeignExchange getById(@PathVariable Long id) {
        return foreignExchangeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Döviz alım/satım oluştur")
    @PreAuthorize("hasAuthority('finance:create')")
    public ForeignExchange create(@RequestBody ForeignExchange exchange) {
        return foreignExchangeService.save(exchange);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Döviz işlem sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        foreignExchangeService.delete(id);
    }
}

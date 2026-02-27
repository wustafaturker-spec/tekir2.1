package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.BankCard;
import com.ut.tekir.service.BankCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-cards")
@RequiredArgsConstructor
@Tag(name = "Bank Cards", description = "Banka kartları")
public class BankCardController {

    private final BankCardService bankCardService;

    @GetMapping
    @Operation(summary = "Banka kartı listesi")
    @PreAuthorize("hasAuthority('bank:read')")
    public List<BankCard> list() {
        return bankCardService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Banka kartı detayı")
    @PreAuthorize("hasAuthority('bank:read')")
    public BankCard get(@PathVariable Long id) {
        return bankCardService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni banka kartı")
    @PreAuthorize("hasAuthority('bank:create')")
    public BankCard create(@Valid @RequestBody BankCard card) {
        return bankCardService.save(card);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Banka kartı güncelle")
    @PreAuthorize("hasAuthority('bank:update')")
    public BankCard update(@PathVariable Long id, @Valid @RequestBody BankCard card) {
        card.setId(id);
        return bankCardService.save(card);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Banka kartı sil")
    @PreAuthorize("hasAuthority('bank:delete')")
    public void delete(@PathVariable Long id) {
        bankCardService.delete(id);
    }
}

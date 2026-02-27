package com.ut.tekir.api.controller;

import com.ut.tekir.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/options")
@RequiredArgsConstructor
@Tag(name = "Options", description = "Sistem ayarları")
public class OptionController {

    private final OptionService optionService;

    @GetMapping("/{key}")
    @Operation(summary = "Ayar getir")
    @PreAuthorize("hasAuthority('option:read')")
    public String get(@PathVariable String key) {
        return optionService.getOption(key);
    }

    @PutMapping("/{key}")
    @Operation(summary = "Ayar güncelle")
    @PreAuthorize("hasAuthority('option:update')")
    public void update(@PathVariable String key, @RequestBody String value) {
        optionService.saveOption(key, value);
    }
}



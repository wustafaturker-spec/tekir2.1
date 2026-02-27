package com.ut.tekir.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "System", description = "Sistem bilgileri")
public class HealthController {

    @GetMapping("/info")
    @Operation(summary = "API bilgisi")
    public Map<String, Object> info() {
        return Map.of(
            "application", "Tekir Modern API",
            "version", "3.0.0-SNAPSHOT",
            "timestamp", LocalDateTime.now()
        );
    }
}



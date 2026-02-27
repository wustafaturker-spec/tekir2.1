package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.portfolio.PortfolioDetailDTO;
import com.ut.tekir.common.dto.portfolio.PortfolioListDTO;
import com.ut.tekir.common.entity.Portfolio;
import com.ut.tekir.service.PortfolioService;
import com.ut.tekir.service.mapper.PortfolioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
@Tag(name = "Portfolios", description = "Portföy tanımları")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioMapper portfolioMapper;

    @GetMapping
    @Operation(summary = "Portföy listesi")
    @PreAuthorize("hasAuthority('finance:read')")
    public List<PortfolioListDTO> list() {
        return portfolioService.findAll().stream()
            .map(portfolioMapper::toListDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Portföy detayı")
    @PreAuthorize("hasAuthority('finance:read')")
    public PortfolioDetailDTO get(@PathVariable Long id) {
        return portfolioMapper.toDetailDTO(portfolioService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Yeni portföy")
    @PreAuthorize("hasAuthority('finance:create')")
    public PortfolioDetailDTO create(@Valid @RequestBody Portfolio portfolio) {
        return portfolioMapper.toDetailDTO(portfolioService.save(portfolio));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Portföy sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        portfolioService.delete(id);
    }
}

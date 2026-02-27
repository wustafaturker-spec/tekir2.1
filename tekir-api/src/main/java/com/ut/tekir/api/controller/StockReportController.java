package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.stock.StockMovementDetailDTO;
import com.ut.tekir.common.dto.stock.StockStatusDTO;
import com.ut.tekir.service.StockReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-report")
@RequiredArgsConstructor
@Tag(name = "Stock Report", description = "Stok durum raporu")
public class StockReportController {

    private final StockReportService stockReportService;

    @GetMapping("/status")
    @Operation(summary = "Tüm ürünlerin stok durumu (giriş/çıkış/bakiye)")
    @PreAuthorize("hasAuthority('product:read')")
    public List<StockStatusDTO> getStatus(@RequestParam(required = false) String search) {
        return stockReportService.getStockStatus(search);
    }

    @GetMapping("/{productId}/movements")
    @Operation(summary = "Bir ürünün detaylı stok hareketleri")
    @PreAuthorize("hasAuthority('product:read')")
    public List<StockMovementDetailDTO> getMovements(@PathVariable Long productId) {
        return stockReportService.getMovements(productId);
    }
}

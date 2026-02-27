package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.ProductTransfer;
import com.ut.tekir.common.entity.ProductVirement;
import com.ut.tekir.common.entity.CountNote;
import com.ut.tekir.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock-movements")
@RequiredArgsConstructor
@Tag(name = "Stock Movements", description = "Stok hareketleri (Transfer, Virman, Sayım)")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/transfers")
    @Operation(summary = "Depo transferi kaydet")
    @PreAuthorize("hasAuthority('stock:create')")
    public ProductTransfer createTransfer(@RequestBody ProductTransfer transfer) {
        return stockMovementService.saveTransfer(transfer);
    }

    @PostMapping("/virements")
    @Operation(summary = "Virman kaydet")
    @PreAuthorize("hasAuthority('stock:create')")
    public ProductVirement createVirement(@RequestBody ProductVirement virement) {
        return stockMovementService.saveVirement(virement);
    }
    
    @PostMapping("/counts")
    @Operation(summary = "Sayım fişi kaydet")
    @PreAuthorize("hasAuthority('stock:create')")
    public CountNote createCount(@RequestBody CountNote countNote) {
        return stockMovementService.saveCountNote(countNote);
    }
}



package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.ProductTransfer;
import com.ut.tekir.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-transfers")
@RequiredArgsConstructor
@Tag(name = "Product Transfers", description = "Ürün transferleri")
public class ProductTransferController {

    private final StockMovementService stockMovementService;

    @GetMapping
    @Operation(summary = "Ürün transfer listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('stock:read')")
    public Page<ProductTransfer> list(@ParameterObject Pageable pageable) {
        return stockMovementService.findAllTransfers(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ürün transfer detayı")
    @PreAuthorize("hasAuthority('stock:read')")
    public ProductTransfer get(@PathVariable Long id) {
        return stockMovementService.getTransferById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni ürün transferi")
    @PreAuthorize("hasAuthority('stock:create')")
    public ProductTransfer create(@RequestBody ProductTransfer transfer) {
        return stockMovementService.saveTransfer(transfer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ürün transfer sil")
    @PreAuthorize("hasAuthority('stock:delete')")
    public void delete(@PathVariable Long id) {
        stockMovementService.deleteTransfer(id);
    }
}

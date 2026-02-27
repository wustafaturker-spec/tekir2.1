package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.ProductVirement;
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
@RequestMapping("/api/v1/product-virements")
@RequiredArgsConstructor
@Tag(name = "Product Virements", description = "Ürün virmanları")
public class ProductVirementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    @Operation(summary = "Ürün virman listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('stock:read')")
    public Page<ProductVirement> list(@ParameterObject Pageable pageable) {
        return stockMovementService.findAllVirements(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ürün virman detayı")
    @PreAuthorize("hasAuthority('stock:read')")
    public ProductVirement get(@PathVariable Long id) {
        return stockMovementService.getVirementById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni ürün virmanı")
    @PreAuthorize("hasAuthority('stock:create')")
    public ProductVirement create(@RequestBody ProductVirement virement) {
        return stockMovementService.saveVirement(virement);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ürün virman sil")
    @PreAuthorize("hasAuthority('stock:delete')")
    public void delete(@PathVariable Long id) {
        stockMovementService.deleteVirement(id);
    }
}

package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.Warehouse;
import com.ut.tekir.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
@Tag(name = "Warehouses", description = "Depo tanımları")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    @Operation(summary = "Depo listesi (paginated)")
    public Page<Warehouse> search(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return warehouseService.search(code, name, active, PageRequest.of(page, size, Sort.by("name")));
    }

    @GetMapping("/all")
    @Operation(summary = "Tüm depolar (dropdown için)")
    public List<Warehouse> listAll() {
        return warehouseService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Depo detayı")
    public Warehouse getById(@PathVariable Long id) {
        return warehouseService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni depo")
    @PreAuthorize("hasAuthority('stock:create')")
    public Warehouse create(@RequestBody Warehouse warehouse) {
        return warehouseService.save(warehouse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Depo güncelle")
    @PreAuthorize("hasAuthority('stock:update')")
    public Warehouse update(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        warehouse.setId(id);
        return warehouseService.save(warehouse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Depo sil")
    @PreAuthorize("hasAuthority('stock:delete')")
    public void delete(@PathVariable Long id) {
        warehouseService.delete(id);
    }
}

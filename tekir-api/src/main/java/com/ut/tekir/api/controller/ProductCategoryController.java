package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.entity.ProductCategory;
import com.ut.tekir.service.ProductCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
@Tag(name = "Product Categories", description = "Ürün kategori yönetimi")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    @Operation(summary = "Ürün kategori listesi")
    public List<ProductCategory> list() {
        return productCategoryService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ürün kategori detay")
    public ProductCategory get(@PathVariable Long id) {
        return productCategoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni ürün kategorisi")
    public ProductCategory create(@RequestBody ProductCategory category) {
        return productCategoryService.save(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ürün kategorisi güncelle")
    public ProductCategory update(@PathVariable Long id, @RequestBody ProductCategory category) {
        category.setId(id);
        return productCategoryService.save(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ürün kategorisi sil")
    public void delete(@PathVariable Long id) {
        productCategoryService.delete(id);
    }
}



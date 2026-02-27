package com.ut.tekir.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.product.ProductDetailDTO;
import com.ut.tekir.common.dto.product.ProductFilterModel;
import com.ut.tekir.common.dto.product.ProductListDTO;
import com.ut.tekir.common.dto.product.ProductSaveRequest;
import com.ut.tekir.common.entity.ProductTxn;
import com.ut.tekir.repository.ProductTxnRepository;
import com.ut.tekir.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Product REST controller — replaces legacy productBrowse.seam+productHome.
 * Full CRUD with pagination, filtering, and autocomplete.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Ürün yönetimi")
public class ProductController {

    private final ProductService productService;
    private final ProductTxnRepository productTxnRepository;

    @GetMapping
    @Operation(summary = "Ürün listesi (sayfalama+filtre)")
    @PreAuthorize("hasAuthority('product:read')")
    public Page<ProductListDTO> list(
            ProductFilterModel filter,
            @PageableDefault(size = 20, sort = "code") Pageable pageable) {
        return productService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ürün detay")
    @PreAuthorize("hasAuthority('product:read')")
    public ProductDetailDTO get(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni ürün oluştur")
    @PreAuthorize("hasAuthority('product:create')")
    public ProductDetailDTO create(@Valid @RequestBody ProductSaveRequest request) {
        return productService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ürün güncelle")
    @PreAuthorize("hasAuthority('product:update')")
    public ProductDetailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ProductSaveRequest request) {
        ProductSaveRequest withId = new ProductSaveRequest(
                id, request.code(), request.name(), request.info(),
                request.productType(), request.categoryId(), request.unit(),
                request.barcode1(), request.barcode2(), request.barcode3(),
                request.image(), request.groupCode(), request.weight(),
                request.buyTaxId(), request.sellTaxId(), request.active());
        return productService.save(withId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ürün sil")
    @PreAuthorize("hasAuthority('product:delete')")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/suggest")
    @Operation(summary = "Ürün arama (autocomplete)")
    @PreAuthorize("hasAuthority('product:read')")
    public java.util.List<com.ut.tekir.common.dto.SuggestDTO> suggest(@RequestParam String q) {
        return productService.suggest(q);
    }

    @GetMapping("/{id}/transactions")
    @Operation(summary = "Ürün stok hareketleri")
    @PreAuthorize("hasAuthority('product:read')")
    public List<ProductTxn> listTransactions(@PathVariable Long id) {
        return productTxnRepository.findByProductIdOrderByDateDesc(id);
    }
}

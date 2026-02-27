package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.PriceList;
import com.ut.tekir.common.entity.PriceListItem;
import com.ut.tekir.service.PriceItemService;
import com.ut.tekir.service.PriceListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/price-lists")
@RequiredArgsConstructor
@Tag(name = "Price Lists", description = "Fiyat listeleri")
public class PriceListController {

    private final PriceListService priceListService;
    private final PriceItemService priceItemService;

    @GetMapping
    @Operation(summary = "Fiyat listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('product:read')")
    public Page<PriceList> list(@ParameterObject Pageable pageable) {
        return priceListService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fiyat listesi detayı")
    @PreAuthorize("hasAuthority('product:read')")
    public PriceList get(@PathVariable Long id) {
        return priceListService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni fiyat listesi")
    @PreAuthorize("hasAuthority('product:create')")
    public PriceList create(@Valid @RequestBody PriceList priceList) {
        return priceListService.save(priceList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Fiyat listesi güncelle")
    @PreAuthorize("hasAuthority('product:update')")
    public PriceList update(@PathVariable Long id, @Valid @RequestBody PriceList priceList) {
        priceList.setId(id);
        return priceListService.save(priceList);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Fiyat listesi sil")
    @PreAuthorize("hasAuthority('product:delete')")
    public void delete(@PathVariable Long id) {
        priceListService.delete(id);
    }

    // --- Items ---

    @GetMapping("/{id}/items")
    @Operation(summary = "Fiyat listesi kalemleri")
    @PreAuthorize("hasAuthority('product:read')")
    public List<PriceListItem> listItems(@PathVariable Long id) {
        return priceItemService.findByPriceListId(id);
    }

    @PostMapping("/{id}/items")
    @Operation(summary = "Fiyat listesi kalemi ekle")
    @PreAuthorize("hasAuthority('product:update')")
    public PriceList addItem(@PathVariable Long id, @Valid @RequestBody PriceListItem item) {
        return priceItemService.addItem(id, item);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    @Operation(summary = "Fiyat listesi kalemi sil")
    @PreAuthorize("hasAuthority('product:update')")
    public void removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        priceItemService.removeItem(id, itemId);
    }
}

package com.ut.tekir.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ut.tekir.common.dto.invoice.InvoiceDetailDTO;
import com.ut.tekir.common.dto.order.OrderDetailDTO;
import com.ut.tekir.common.dto.order.OrderFilterModel;
import com.ut.tekir.common.dto.order.OrderListDTO;
import com.ut.tekir.common.dto.order.OrderSaveRequest;
import com.ut.tekir.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Order REST controller — replaces legacy orderNoteBrowse.seam + orderNoteHome.
 * Full CRUD with pagination and filtering.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Sipariş yönetimi")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Sipariş listesi (sayfalama+filtre)")
    @PreAuthorize("hasAuthority('order:read')")
    public Page<OrderListDTO> list(
            OrderFilterModel filter,
            @PageableDefault(size = 20, sort = "date") Pageable pageable) {
        return orderService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Sipariş detay")
    @PreAuthorize("hasAuthority('order:read')")
    public OrderDetailDTO get(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni sipariş oluştur")
    @PreAuthorize("hasAuthority('order:create')")
    public OrderDetailDTO create(@Valid @RequestBody OrderSaveRequest request) {
        return orderService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sipariş güncelle")
    @PreAuthorize("hasAuthority('order:update')")
    public OrderDetailDTO update(
            @PathVariable Long id,
            @Valid @RequestBody OrderSaveRequest request) {
        OrderSaveRequest withId = new OrderSaveRequest(
                id, request.tradeAction(), request.serial(), request.reference(),
                request.date(), request.contactId(), request.warehouseId(),
                request.info(), request.items());
        return orderService.save(withId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sipariş sil")
    @PreAuthorize("hasAuthority('order:delete')")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    @PostMapping("/{id}/convert-to-invoice")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Siparişi faturaya dönüştür")
    @PreAuthorize("hasAuthority('order:update') and hasAuthority('invoice:create')")
    public InvoiceDetailDTO convertToInvoice(@PathVariable Long id) {
        return orderService.convertToInvoice(id);
    }
}

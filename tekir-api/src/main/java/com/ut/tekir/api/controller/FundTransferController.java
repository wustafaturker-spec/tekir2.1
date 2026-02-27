package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.FundTransfer;
import com.ut.tekir.service.FundTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/fund-transfers")
@RequiredArgsConstructor
@Tag(name = "Fund Transfers", description = "Kasa/Banka virmanları")
public class FundTransferController {

    private final FundTransferService fundTransferService;

    @GetMapping
    @Operation(summary = "Virman listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('finance:read')")
    public Page<FundTransfer> list(@ParameterObject Pageable pageable) {
        return fundTransferService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Virman detayı")
    @PreAuthorize("hasAuthority('finance:read')")
    public FundTransfer get(@PathVariable Long id) {
        return fundTransferService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni virman")
    @PreAuthorize("hasAuthority('finance:create')")
    public FundTransfer create(@Valid @RequestBody FundTransfer transfer) {
        return fundTransferService.save(transfer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Virman sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        fundTransferService.delete(id);
    }
}

package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.payment.PaymentListDTO;
import com.ut.tekir.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
@Tag(name = "Collections", description = "Tahsilatlar")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    @Operation(summary = "Tahsilat listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('payment:read')")
    public Page<PaymentListDTO> list(@ParameterObject Pageable pageable) {
        return collectionService.findCollections(pageable);
    }
}

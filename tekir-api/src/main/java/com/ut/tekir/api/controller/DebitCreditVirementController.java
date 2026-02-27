package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.debitcredit.DebitCreditVirementDetailDTO;
import com.ut.tekir.common.dto.debitcredit.DebitCreditVirementListDTO;
import com.ut.tekir.common.entity.DebitCreditVirement;
import com.ut.tekir.service.DebitCreditVirementService;
import com.ut.tekir.service.mapper.DebitCreditVirementMapper;
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
@RequestMapping("/api/v1/debit-credit-virements")
@RequiredArgsConstructor
@Tag(name = "Debit Credit Virements", description = "Borç/Alacak virmanları")
public class DebitCreditVirementController {

    private final DebitCreditVirementService virementService;
    private final DebitCreditVirementMapper virementMapper;

    @GetMapping
    @Operation(summary = "Virman listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('finance:read')")
    public Page<DebitCreditVirementListDTO> list(@ParameterObject Pageable pageable) {
        return virementService.findAll(pageable).map(virementMapper::toListDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Virman detayı")
    @PreAuthorize("hasAuthority('finance:read')")
    public DebitCreditVirementDetailDTO get(@PathVariable Long id) {
        return virementMapper.toDetailDTO(virementService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni virman")
    @PreAuthorize("hasAuthority('finance:create')")
    public DebitCreditVirementDetailDTO create(@RequestBody DebitCreditVirement virement) {
        return virementMapper.toDetailDTO(virementService.save(virement));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Virman sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        virementService.delete(id);
    }
}

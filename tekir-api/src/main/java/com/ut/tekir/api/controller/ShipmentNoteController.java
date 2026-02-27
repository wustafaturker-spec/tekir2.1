package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.shipment.ShipmentItemDTO;
import com.ut.tekir.common.dto.shipment.ShipmentNoteDetailDTO;
import com.ut.tekir.common.dto.shipment.ShipmentNoteListDTO;
import com.ut.tekir.common.entity.ShipmentNote;
import com.ut.tekir.service.ShipmentNoteService;
import com.ut.tekir.service.mapper.ShipmentNoteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipment-notes")
@RequiredArgsConstructor
@Tag(name = "Shipment Notes", description = "İrsaliye yönetimi")
public class ShipmentNoteController {

    private final ShipmentNoteService shipmentNoteService;
    private final ShipmentNoteMapper mapper;

    @GetMapping
    @Operation(summary = "İrsaliye listesi (sayfalı)")
    public Page<ShipmentNoteListDTO> list(Pageable pageable) {
        return shipmentNoteService.findAll(pageable).map(mapper::toListDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "İrsaliye detayı")
    public ShipmentNoteDetailDTO getById(@PathVariable Long id) {
        return mapper.toDetailDTO(shipmentNoteService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "İrsaliye oluştur")
    @PreAuthorize("hasAuthority('trade:create')")
    public ShipmentNoteDetailDTO create(@RequestBody ShipmentNote note) {
        return mapper.toDetailDTO(shipmentNoteService.save(note));
    }

    @PutMapping("/{id}")
    @Operation(summary = "İrsaliye güncelle")
    @PreAuthorize("hasAuthority('trade:update')")
    public ShipmentNoteDetailDTO update(@PathVariable Long id, @RequestBody ShipmentNote note) {
        note.setId(id);
        return mapper.toDetailDTO(shipmentNoteService.save(note));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "İrsaliye sil")
    @PreAuthorize("hasAuthority('trade:delete')")
    public void delete(@PathVariable Long id) {
        shipmentNoteService.delete(id);
    }
}

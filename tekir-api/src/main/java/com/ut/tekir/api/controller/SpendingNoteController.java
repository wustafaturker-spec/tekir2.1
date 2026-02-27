package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.spendingnote.SpendingNoteDetailDTO;
import com.ut.tekir.common.dto.spendingnote.SpendingNoteListDTO;
import com.ut.tekir.common.entity.SpendingNote;
import com.ut.tekir.service.SpendingNoteService;
import com.ut.tekir.service.mapper.SpendingNoteMapper;
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
@RequestMapping("/api/v1/spending-notes")
@RequiredArgsConstructor
@Tag(name = "Spending Notes", description = "Harcama notları")
public class SpendingNoteController {

    private final SpendingNoteService spendingNoteService;
    private final SpendingNoteMapper spendingNoteMapper;

    @GetMapping
    @Operation(summary = "Harcama notu listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('expense:read')")
    public Page<SpendingNoteListDTO> list(@ParameterObject Pageable pageable) {
        return spendingNoteService.findAll(pageable).map(spendingNoteMapper::toListDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Harcama notu detayı")
    @PreAuthorize("hasAuthority('expense:read')")
    public SpendingNoteDetailDTO get(@PathVariable Long id) {
        return spendingNoteMapper.toDetailDTO(spendingNoteService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni harcama notu")
    @PreAuthorize("hasAuthority('expense:create')")
    public SpendingNoteDetailDTO create(@RequestBody SpendingNote note) {
        return spendingNoteMapper.toDetailDTO(spendingNoteService.save(note));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Harcama notu sil")
    @PreAuthorize("hasAuthority('expense:delete')")
    public void delete(@PathVariable Long id) {
        spendingNoteService.delete(id);
    }
}

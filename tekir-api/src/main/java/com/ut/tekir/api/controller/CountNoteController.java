package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.countnote.CountNoteDetailDTO;
import com.ut.tekir.common.dto.countnote.CountNoteListDTO;
import com.ut.tekir.common.entity.CountNote;
import com.ut.tekir.service.CountNoteService;
import com.ut.tekir.service.mapper.CountNoteMapper;
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
@RequestMapping("/api/v1/count-notes")
@RequiredArgsConstructor
@Tag(name = "Count Notes", description = "Sayım notları")
public class CountNoteController {

    private final CountNoteService countNoteService;
    private final CountNoteMapper countNoteMapper;

    @GetMapping
    @Operation(summary = "Sayım notu listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('stock:read')")
    public Page<CountNoteListDTO> list(@ParameterObject Pageable pageable) {
        return countNoteService.findAll(pageable).map(countNoteMapper::toListDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Sayım notu detayı")
    @PreAuthorize("hasAuthority('stock:read')")
    public CountNoteDetailDTO get(@PathVariable Long id) {
        return countNoteMapper.toDetailDTO(countNoteService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni sayım notu")
    @PreAuthorize("hasAuthority('stock:create')")
    public CountNoteDetailDTO create(@RequestBody CountNote note) {
        return countNoteMapper.toDetailDTO(countNoteService.save(note));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sayım notu sil")
    @PreAuthorize("hasAuthority('stock:delete')")
    public void delete(@PathVariable Long id) {
        countNoteService.delete(id);
    }
}

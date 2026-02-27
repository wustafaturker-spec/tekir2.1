package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.documentmatch.DocumentMatchListDTO;
import com.ut.tekir.common.entity.DocumentMatch;
import com.ut.tekir.service.DocumentMatchService;
import com.ut.tekir.service.mapper.DocumentMatchMapper;
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
@RequestMapping("/api/v1/document-matches")
@RequiredArgsConstructor
@Tag(name = "Document Matches", description = "Belge eşleştirme")
public class DocumentMatchController {

    private final DocumentMatchService documentMatchService;
    private final DocumentMatchMapper documentMatchMapper;

    @GetMapping
    @Operation(summary = "Belge eşleştirme listesi")
    @PreAuthorize("hasAuthority('finance:read')")
    public Page<DocumentMatchListDTO> list(@ParameterObject Pageable pageable) {
        return documentMatchService.findAll(pageable).map(documentMatchMapper::toListDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni belge eşleştirme")
    @PreAuthorize("hasAuthority('finance:create')")
    public DocumentMatchListDTO create(@RequestBody DocumentMatch match) {
        return documentMatchMapper.toListDTO(documentMatchService.save(match));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Belge eşleştirme sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        documentMatchService.delete(id);
    }
}

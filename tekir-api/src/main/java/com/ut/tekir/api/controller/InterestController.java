package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.interest.InterestDetailDTO;
import com.ut.tekir.common.dto.interest.InterestListDTO;
import com.ut.tekir.common.entity.Interest;
import com.ut.tekir.service.InterestService;
import com.ut.tekir.service.mapper.InterestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/interests")
@RequiredArgsConstructor
@Tag(name = "Interests", description = "Faiz tanımları")
public class InterestController {

    private final InterestService interestService;
    private final InterestMapper interestMapper;

    @GetMapping
    @Operation(summary = "Faiz listesi")
    @PreAuthorize("hasAuthority('finance:read')")
    public List<InterestListDTO> list() {
        return interestService.findAll().stream()
            .map(interestMapper::toListDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Faiz detayı")
    @PreAuthorize("hasAuthority('finance:read')")
    public InterestDetailDTO get(@PathVariable Long id) {
        return interestMapper.toDetailDTO(interestService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Yeni faiz")
    @PreAuthorize("hasAuthority('finance:create')")
    public InterestDetailDTO create(@Valid @RequestBody Interest interest) {
        return interestMapper.toDetailDTO(interestService.save(interest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Faiz sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        interestService.delete(id);
    }
}

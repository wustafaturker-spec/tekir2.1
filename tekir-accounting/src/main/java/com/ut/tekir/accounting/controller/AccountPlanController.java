package com.ut.tekir.accounting.controller;

import com.ut.tekir.accounting.dto.AccountImportResultDTO;
import com.ut.tekir.accounting.dto.AccountPlanDTO;
import com.ut.tekir.accounting.dto.AccountPlanSaveRequest;
import com.ut.tekir.accounting.service.AccountPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounting/plans")
@RequiredArgsConstructor
public class AccountPlanController {

    private final AccountPlanService service;

    @GetMapping
    @PreAuthorize("hasAuthority('accounting:read')")
    public ResponseEntity<List<AccountPlanDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('accounting:read')")
    public ResponseEntity<AccountPlanDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('accounting:create')")
    public ResponseEntity<AccountPlanDTO> create(@Valid @RequestBody AccountPlanSaveRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('accounting:update')")
    public ResponseEntity<AccountPlanDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody AccountPlanSaveRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('accounting:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('accounting:update')")
    public ResponseEntity<AccountPlanDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(service.activate(id));
    }

    @PostMapping("/load-tdhp")
    @PreAuthorize("hasAuthority('accounting:create')")
    public ResponseEntity<AccountPlanDTO> loadTdhp() {
        return ResponseEntity.ok(service.loadTdhp());
    }

    @GetMapping("/import/template")
    @PreAuthorize("hasAuthority('accounting:read')")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        byte[] template = service.getImportTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment().filename("hesap-plani-sablonu.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(template);
    }

    @PostMapping("/{id}/import")
    @PreAuthorize("hasAuthority('accounting:import')")
    public ResponseEntity<AccountImportResultDTO> importExcel(@PathVariable Long id,
                                                               @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.importFromExcel(id, file));
    }
}

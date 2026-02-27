package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.accountcreditnote.AccountCreditNoteDetailDTO;
import com.ut.tekir.common.dto.accountcreditnote.AccountCreditNoteListDTO;
import com.ut.tekir.common.entity.AccountCreditNote;
import com.ut.tekir.service.AccountCreditNoteService;
import com.ut.tekir.service.mapper.AccountCreditNoteMapper;
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
@RequestMapping("/api/v1/account-credit-notes")
@RequiredArgsConstructor
@Tag(name = "Account Credit Notes", description = "Hesap alacak dekontları")
public class AccountCreditNoteController {

    private final AccountCreditNoteService accountCreditNoteService;
    private final AccountCreditNoteMapper accountCreditNoteMapper;

    @GetMapping
    @Operation(summary = "Alacak dekontu listesi (Sayfalı)")
    @PreAuthorize("hasAuthority('finance:read')")
    public Page<AccountCreditNoteListDTO> list(@ParameterObject Pageable pageable) {
        return accountCreditNoteService.findAll(pageable).map(accountCreditNoteMapper::toListDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Alacak dekontu detayı")
    @PreAuthorize("hasAuthority('finance:read')")
    public AccountCreditNoteDetailDTO get(@PathVariable Long id) {
        return accountCreditNoteMapper.toDetailDTO(accountCreditNoteService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni alacak dekontu")
    @PreAuthorize("hasAuthority('finance:create')")
    public AccountCreditNoteDetailDTO create(@RequestBody AccountCreditNote note) {
        return accountCreditNoteMapper.toDetailDTO(accountCreditNoteService.save(note));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Alacak dekontu sil")
    @PreAuthorize("hasAuthority('finance:delete')")
    public void delete(@PathVariable Long id) {
        accountCreditNoteService.delete(id);
    }
}

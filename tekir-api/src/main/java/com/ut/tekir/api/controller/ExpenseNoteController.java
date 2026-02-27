package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.expense.ExpenseNoteDetailDTO;
import com.ut.tekir.common.dto.expense.ExpenseNoteListDTO;
import com.ut.tekir.common.entity.ExpenseNote;
import com.ut.tekir.service.ExpenseNoteService;
import com.ut.tekir.service.mapper.ExpenseNoteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense-notes")
@RequiredArgsConstructor
@Tag(name = "Expense Notes", description = "Masraf fişi yönetimi")
public class ExpenseNoteController {

    private final ExpenseNoteService expenseNoteService;
    private final ExpenseNoteMapper mapper;

    @GetMapping
    @Operation(summary = "Masraf fişi listesi (sayfalı)")
    public Page<ExpenseNoteListDTO> list(Pageable pageable) {
        return expenseNoteService.findAll(pageable).map(mapper::toListDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Masraf fişi detayı")
    public ExpenseNoteDetailDTO getById(@PathVariable Long id) {
        return mapper.toDetailDTO(expenseNoteService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Masraf fişi oluştur")
    @PreAuthorize("hasAuthority('expense:create')")
    public ExpenseNoteDetailDTO create(@RequestBody ExpenseNote note) {
        return mapper.toDetailDTO(expenseNoteService.save(note));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Masraf fişi güncelle")
    @PreAuthorize("hasAuthority('expense:update')")
    public ExpenseNoteDetailDTO update(@PathVariable Long id, @RequestBody ExpenseNote note) {
        note.setId(id);
        return mapper.toDetailDTO(expenseNoteService.save(note));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Masraf fişi sil")
    @PreAuthorize("hasAuthority('expense:delete')")
    public void delete(@PathVariable Long id) {
        expenseNoteService.delete(id);
    }
}

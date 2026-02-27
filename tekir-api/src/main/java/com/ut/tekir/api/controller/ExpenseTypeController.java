package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.ExpenseType;
import com.ut.tekir.service.ExpenseTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense-types")
@RequiredArgsConstructor
@Tag(name = "Expense Types", description = "Gider türü tanımları")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @GetMapping
    @Operation(summary = "Gider türü listesi")
    @PreAuthorize("hasAuthority('expense:read')")
    public List<ExpenseType> list() {
        return expenseTypeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gider türü detayı")
    @PreAuthorize("hasAuthority('expense:read')")
    public ExpenseType get(@PathVariable Long id) {
        return expenseTypeService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni gider türü")
    @PreAuthorize("hasAuthority('expense:create')")
    public ExpenseType create(@Valid @RequestBody ExpenseType expenseType) {
        return expenseTypeService.save(expenseType);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Gider türü güncelle")
    @PreAuthorize("hasAuthority('expense:update')")
    public ExpenseType update(@PathVariable Long id, @Valid @RequestBody ExpenseType expenseType) {
        expenseType.setId(id);
        return expenseTypeService.save(expenseType);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Gider türü sil")
    @PreAuthorize("hasAuthority('expense:delete')")
    public void delete(@PathVariable Long id) {
        expenseTypeService.delete(id);
    }
}

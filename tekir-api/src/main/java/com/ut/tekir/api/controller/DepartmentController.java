package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.Department;
import com.ut.tekir.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(name = "Departments", description = "Departman tanımları")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    @Operation(summary = "Departman listesi")
    @PreAuthorize("hasAuthority('department:read')")
    public List<Department> list() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Departman detayı")
    @PreAuthorize("hasAuthority('department:read')")
    public Department get(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni departman")
    @PreAuthorize("hasAuthority('department:create')")
    public Department create(@Valid @RequestBody Department department) {
        return departmentService.save(department);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Departman güncelle")
    @PreAuthorize("hasAuthority('department:update')")
    public Department update(@PathVariable Long id, @Valid @RequestBody Department department) {
        department.setId(id);
        return departmentService.save(department);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Departman sil")
    @PreAuthorize("hasAuthority('department:delete')")
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }
}

package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.Employee;
import com.ut.tekir.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Personel tanımları")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Personel listesi")
    @PreAuthorize("hasAuthority('employee:read')")
    public List<Employee> list(@RequestParam(required = false) Long departmentId) {
        if (departmentId != null) {
            return employeeService.findByDepartmentId(departmentId);
        }
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Personel detayı")
    @PreAuthorize("hasAuthority('employee:read')")
    public Employee get(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Yeni personel")
    @PreAuthorize("hasAuthority('employee:create')")
    public Employee create(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Personel güncelle")
    @PreAuthorize("hasAuthority('employee:update')")
    public Employee update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        employee.setId(id);
        return employeeService.save(employee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Personel sil")
    @PreAuthorize("hasAuthority('employee:delete')")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}

package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Employee;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Employee> findByDepartmentId(Long departmentId) {
        return employeeRepository.findAll((root, query, cb) ->
            cb.equal(root.get("department").get("id"), departmentId)
        );
    }

    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Employee", id));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee", id);
        }
        employeeRepository.deleteById(id);
    }
}

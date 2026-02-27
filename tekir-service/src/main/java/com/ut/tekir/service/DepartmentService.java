package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Department;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Department getById(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Department", id));
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department", id);
        }
        departmentRepository.deleteById(id);
    }
}

package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.ExpenseType;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ExpenseTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;

    @Transactional(readOnly = true)
    public List<ExpenseType> findAll() {
        return expenseTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ExpenseType getById(Long id) {
        return expenseTypeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ExpenseType", id));
    }

    public ExpenseType save(ExpenseType expenseType) {
        return expenseTypeRepository.save(expenseType);
    }

    public void delete(Long id) {
        if (!expenseTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("ExpenseType", id);
        }
        expenseTypeRepository.deleteById(id);
    }
}

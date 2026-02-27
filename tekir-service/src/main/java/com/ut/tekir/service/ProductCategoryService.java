package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.ProductCategory;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * ProductCategory service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductCategory getById(Long id) {
        return productCategoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ProductCategory", id));
    }

    public ProductCategory save(ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    public void delete(Long id) {
        if (!productCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("ProductCategory", id);
        }
        productCategoryRepository.deleteById(id);
    }
}

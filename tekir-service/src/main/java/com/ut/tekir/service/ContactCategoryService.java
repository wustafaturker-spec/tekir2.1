package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.ContactCategory;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactCategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * ContactCategory service — replaces legacy ContactCategoryHomeBean.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ContactCategoryService {

    private final ContactCategoryRepository contactCategoryRepository;

    @Transactional(readOnly = true)
    public List<ContactCategory> findAll() {
        return contactCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ContactCategory getById(Long id) {
        return contactCategoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ContactCategory", id));
    }

    public ContactCategory save(ContactCategory category) {
        return contactCategoryRepository.save(category);
    }

    public void delete(Long id) {
        if (!contactCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("ContactCategory", id);
        }
        contactCategoryRepository.deleteById(id);
    }
}

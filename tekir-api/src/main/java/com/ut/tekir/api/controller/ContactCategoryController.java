package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.ContactCategory;
import com.ut.tekir.repository.ContactCategoryRepository; // Direct repo use for simple CRUD or Service?
// Usually better to have Service. But for simple categories Repo is often used in Controller directly in simple apps.
// Ideally should use ContactService or ContactCategoryService. 
// checking ContactService... it usually handles Contacts.
// I will use Repository directly for now as per "simple migration" unless logic is needed.
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact-categories")
@RequiredArgsConstructor
@Tag(name = "Contact Categories", description = "Cari kategorileri")
public class ContactCategoryController {

    private final ContactCategoryRepository contactCategoryRepository;

    @GetMapping
    @Operation(summary = "Kategori listesi")
    public List<ContactCategory> list() {
        return contactCategoryRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "Yeni kategori")
    @PreAuthorize("hasAuthority('contact:create')")
    public ContactCategory create(@RequestBody ContactCategory category) {
        return contactCategoryRepository.save(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kategori güncelle")
    @PreAuthorize("hasAuthority('contact:update')")
    public ContactCategory update(@PathVariable Long id, @RequestBody ContactCategory category) {
        category.setId(id);
        return contactCategoryRepository.save(category);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Kategori sil")
    @PreAuthorize("hasAuthority('contact:delete')")
    public void delete(@PathVariable Long id) {
        contactCategoryRepository.deleteById(id);
    }
}



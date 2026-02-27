package com.ut.tekir.service;

import com.ut.tekir.common.entity.Interest;
import com.ut.tekir.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    public java.util.List<Interest> findAll() {
        return interestRepository.findAll();
    }

    public Page<Interest> findAll(Pageable pageable) {
        return interestRepository.findAll(pageable);
    }

    public Interest findById(Long id) {
        return interestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interest not found: " + id));
    }

    public Interest save(Interest interest) {
        return interestRepository.save(interest);
    }

    public void delete(Long id) {
        interestRepository.deleteById(id);
    }
}

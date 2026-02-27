package com.ut.tekir.service;

import com.ut.tekir.common.entity.Portfolio;
import com.ut.tekir.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public java.util.List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public Page<Portfolio> findAll(Pageable pageable) {
        return portfolioRepository.findAll(pageable);
    }

    public Portfolio findById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + id));
    }

    public Portfolio save(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }
}

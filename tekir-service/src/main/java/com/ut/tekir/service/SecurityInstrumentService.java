package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Security;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.SecurityRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityInstrumentService {

    private final SecurityRepository securityRepository;

    @Transactional(readOnly = true)
    public List<Security> findAll() {
        return securityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Security getById(Long id) {
        return securityRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Security", id));
    }

    public Security save(Security security) {
        return securityRepository.save(security);
    }

    public void delete(Long id) {
        if (!securityRepository.existsById(id)) {
            throw new EntityNotFoundException("Security", id);
        }
        securityRepository.deleteById(id);
    }
}

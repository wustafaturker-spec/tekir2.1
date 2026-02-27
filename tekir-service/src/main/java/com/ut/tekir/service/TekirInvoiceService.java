package com.ut.tekir.service;

import com.ut.tekir.common.entity.TekirInvoice;
import com.ut.tekir.repository.TekirInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class TekirInvoiceService {

    private final TekirInvoiceRepository tekirInvoiceRepository;

    public Page<TekirInvoice> findAll(Pageable pageable) {
        return tekirInvoiceRepository.findAll(pageable);
    }

    public TekirInvoice findById(Long id) {
        return tekirInvoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TekirInvoice not found: " + id));
    }

    public TekirInvoice save(TekirInvoice invoice) {
        return tekirInvoiceRepository.save(invoice);
    }

    public void delete(Long id) {
        tekirInvoiceRepository.deleteById(id);
    }
}

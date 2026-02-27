package com.ut.tekir.service;

import com.ut.tekir.common.entity.ForeignExchange;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.ForeignExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ForeignExchangeService {

    private final ForeignExchangeRepository foreignExchangeRepository;
    private final SequenceService sequenceService;

    public Page<ForeignExchange> findAll(Pageable pageable) {
        return foreignExchangeRepository.findAll(pageable);
    }

    public ForeignExchange findById(Long id) {
        return foreignExchangeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ForeignExchange not found: " + id));
    }

    public ForeignExchange save(ForeignExchange exchange) {
        if (exchange.getId() == null) {
            exchange.setCode(sequenceService.getNewNumber(DocumentType.ForeignExchange.name(), 6));
            exchange.setDate(LocalDate.now());
        }
        return foreignExchangeRepository.save(exchange);
    }

    public void delete(Long id) {
        foreignExchangeRepository.deleteById(id);
    }
}

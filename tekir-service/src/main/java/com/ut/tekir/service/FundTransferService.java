package com.ut.tekir.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.FundTransfer;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.FundTransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FundTransferService {

    private final FundTransferRepository fundTransferRepository;
    private final SequenceService sequenceService;

    @Transactional(readOnly = true)
    public Page<FundTransfer> findAll(Pageable pageable) {
        return fundTransferRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public FundTransfer getById(Long id) {
        return fundTransferRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("FundTransfer", id));
    }

    public FundTransfer save(FundTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setSerial(sequenceService.getNewNumber(DocumentType.FundTransfer.name() + ".serial", 6));
            transfer.setCode(sequenceService.getNewNumber(DocumentType.FundTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return fundTransferRepository.save(transfer);
    }

    public void delete(Long id) {
        if (!fundTransferRepository.existsById(id)) {
            throw new EntityNotFoundException("FundTransfer", id);
        }
        fundTransferRepository.deleteById(id);
    }
}

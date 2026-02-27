package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.Bank;
import com.ut.tekir.common.entity.BankBranch;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.BankBranchRepository;
import com.ut.tekir.repository.BankRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankBranchService {

    private final BankBranchRepository bankBranchRepository;
    private final BankRepository bankRepository;

    @Transactional(readOnly = true)
    public List<BankBranch> findAll() {
        return bankBranchRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BankBranch> findByBankId(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
            .orElseThrow(() -> new EntityNotFoundException("Bank", bankId));
        return bankBranchRepository.findByBank(bank);
    }

    @Transactional(readOnly = true)
    public BankBranch getById(Long id) {
        return bankBranchRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("BankBranch", id));
    }

    public BankBranch save(BankBranch branch) {
        return bankBranchRepository.save(branch);
    }

    public void delete(Long id) {
        if (!bankBranchRepository.existsById(id)) {
            throw new EntityNotFoundException("BankBranch", id);
        }
        bankBranchRepository.deleteById(id);
    }
}

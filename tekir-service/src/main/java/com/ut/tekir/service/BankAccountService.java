package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.common.entity.BankBranch;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.BankAccountRepository;
import com.ut.tekir.repository.BankBranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankBranchRepository bankBranchRepository;

    @Transactional(readOnly = true)
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BankAccount> findByBranchId(Long branchId) {
        BankBranch branch = bankBranchRepository.findById(branchId)
            .orElseThrow(() -> new EntityNotFoundException("BankBranch", branchId));
        return bankAccountRepository.findByBankBranch(branch);
    }

    @Transactional(readOnly = true)
    public BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("BankAccount", id));
    }

    public BankAccount save(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public void delete(Long id) {
        if (!bankAccountRepository.existsById(id)) {
            throw new EntityNotFoundException("BankAccount", id);
        }
        bankAccountRepository.deleteById(id);
    }
}

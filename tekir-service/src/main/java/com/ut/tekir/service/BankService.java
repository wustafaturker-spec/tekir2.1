package com.ut.tekir.service;

import com.ut.tekir.common.dto.bank.*;
import com.ut.tekir.common.entity.Bank;
import com.ut.tekir.common.entity.BankAccount;
import com.ut.tekir.common.entity.BankBranch;
import com.ut.tekir.repository.BankAccountRepository;
import com.ut.tekir.repository.BankBranchRepository;
import com.ut.tekir.repository.BankRepository;
import com.ut.tekir.service.mapper.BankMapper;
import com.ut.tekir.service.spec.BankSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;
    private final BankBranchRepository bankBranchRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankMapper bankMapper;

    public Page<BankListDTO> search(BankFilterModel filter, Pageable pageable) {
        return bankRepository.findAll(BankSpecifications.maxFilter(filter), pageable)
                .map(bankMapper::toListDTO);
    }

    public BankDetailDTO get(Long id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found: " + id));
        
        BankDetailDTO dto = bankMapper.toDetailDTO(bank);
        // Map branches and accounts manually or via mapper if supported
        // BankMapper has toBranchDTOList and toAccountDTOList
        List<BankBranch> branches = bankBranchRepository.findByBank(bank);
        dto = new BankDetailDTO(
            dto.id(), dto.code(), dto.name(), dto.swiftCode(), dto.active(),
            bankMapper.toBranchDTOList(branches),
            bankMapper.toAccountDTOList(bankAccountRepository.findByBankBranch_Bank(bank)),
            dto.createDate(), dto.createUser(), dto.updateDate()
        );
        return dto;
    }

    @Transactional
    public BankDetailDTO create(BankSaveRequest request) {
        Bank bank = new Bank();
        bank.setCode(request.code());
        bank.setName(request.name());
        bank.setSwiftCode(request.swiftCode());
        bank.setActive(request.active() != null ? request.active() : true);
        
        bank = bankRepository.save(bank);
        return get(bank.getId());
    }

    @Transactional
    public BankDetailDTO update(Long id, BankSaveRequest request) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found: " + id));
        
        bank.setCode(request.code());
        bank.setName(request.name());
        bank.setSwiftCode(request.swiftCode());
        bank.setActive(request.active() != null ? request.active() : true);
        
        bank = bankRepository.save(bank);
        return get(bank.getId());
    }

    @Transactional
    public void delete(Long id) {
        bankRepository.deleteById(id);
    }

    // --- Branch & Account Management ---

    public List<BankBranchDTO> getBranches(Long bankId) {
        Bank bank = bankRepository.getReferenceById(bankId);
        return bankMapper.toBranchDTOList(bankBranchRepository.findByBank(bank));
    }
    
    @Transactional
    public BankBranchDTO saveBranch(BankBranchDTO dto) {
        BankBranch branch = new BankBranch();
        if (dto.id() != null) {
            branch = bankBranchRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));
        }
        branch.setCode(dto.code());
        branch.setName(dto.name());
        
        if (dto.bankId() != null) {
            branch.setBank(bankRepository.getReferenceById(dto.bankId()));
        }
        
        branch = bankBranchRepository.save(branch);
        return bankMapper.toBranchDTO(branch);
    }
    
    public List<BankAccountDTO> getAccounts(Long branchId) {
        BankBranch branch = bankBranchRepository.getReferenceById(branchId);
        return bankMapper.toAccountDTOList(bankAccountRepository.findByBankBranch(branch));
    }

    @Transactional
    public BankAccountDTO saveAccount(BankAccountDTO dto) {
        BankAccount account = new BankAccount();
        if (dto.id() != null) {
            account = bankAccountRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        }
        account.setName(dto.name());
        account.setAccountNo(dto.accountNo());
        account.setIban(dto.iban());
        account.setCurrency(dto.currency());
        
        if (dto.bankBranchId() != null) {
            account.setBankBranch(bankBranchRepository.getReferenceById(dto.bankBranchId()));
        }
        
        account = bankAccountRepository.save(account);
        return bankMapper.toAccountDTO(account);
    }
}


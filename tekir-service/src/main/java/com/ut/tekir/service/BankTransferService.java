package com.ut.tekir.service;

import com.ut.tekir.common.entity.*;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.repository.*;
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
public class BankTransferService {

    private final BankToBankTransferRepository bankToBankTransferRepository;
    private final BankToContactTransferRepository bankToContactTransferRepository;
    private final BankToAccountTransferRepository bankToAccountTransferRepository;
    private final ContactToBankTransferRepository contactToBankTransferRepository;
    private final AccountToBankTransferRepository accountToBankTransferRepository;
    private final FundTransferRepository fundTransferRepository;
    private final SequenceService sequenceService;

    // --- Bank to Bank ---

    public BankToBankTransfer saveBankToBank(BankToBankTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setCode(sequenceService.getNewNumber(DocumentType.BankToBankTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return bankToBankTransferRepository.save(transfer);
    }

    public Page<BankToBankTransfer> findAllBankToBank(Pageable pageable) {
        return bankToBankTransferRepository.findAll(pageable);
    }

    // --- Bank to Contact ---

    public BankToContactTransfer saveBankToContact(BankToContactTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setCode(sequenceService.getNewNumber(DocumentType.BankToContactTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return bankToContactTransferRepository.save(transfer);
    }

    public Page<BankToContactTransfer> findAllBankToContact(Pageable pageable) {
        return bankToContactTransferRepository.findAll(pageable);
    }

    // --- Bank to Account ---

    public BankToAccountTransfer saveBankToAccount(BankToAccountTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setCode(sequenceService.getNewNumber(DocumentType.BankToAccountTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return bankToAccountTransferRepository.save(transfer);
    }

    public Page<BankToAccountTransfer> findAllBankToAccount(Pageable pageable) {
        return bankToAccountTransferRepository.findAll(pageable);
    }

    // --- Contact to Bank ---

    public ContactToBankTransfer saveContactToBank(ContactToBankTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setCode(sequenceService.getNewNumber(DocumentType.ContactToBankTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return contactToBankTransferRepository.save(transfer);
    }

    public Page<ContactToBankTransfer> findAllContactToBank(Pageable pageable) {
        return contactToBankTransferRepository.findAll(pageable);
    }

    // --- Account to Bank ---

    public AccountToBankTransfer saveAccountToBank(AccountToBankTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setCode(sequenceService.getNewNumber(DocumentType.AccountToBankTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return accountToBankTransferRepository.save(transfer);
    }

    public Page<AccountToBankTransfer> findAllAccountToBank(Pageable pageable) {
        return accountToBankTransferRepository.findAll(pageable);
    }

    // --- Fund Transfer (Generic) ---

    public FundTransfer saveFundTransfer(FundTransfer transfer) {
        if (transfer.getId() == null) {
            transfer.setCode(sequenceService.getNewNumber(DocumentType.FundTransfer.name(), 6));
            transfer.setDate(LocalDate.now());
        }
        return fundTransferRepository.save(transfer);
    }

    public Page<FundTransfer> findAllFundTransfers(Pageable pageable) {
        return fundTransferRepository.findAll(pageable);
    }
}

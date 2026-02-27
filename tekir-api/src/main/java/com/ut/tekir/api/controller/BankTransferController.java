package com.ut.tekir.api.controller;

import com.ut.tekir.common.entity.AccountToBankTransfer;
import com.ut.tekir.common.entity.BankToAccountTransfer;
import com.ut.tekir.common.entity.BankToBankTransfer;
import com.ut.tekir.common.entity.BankToContactTransfer;
import com.ut.tekir.common.entity.ContactToBankTransfer;
import com.ut.tekir.service.BankTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-transfers")
@RequiredArgsConstructor
@Tag(name = "Bank Transfers", description = "Banka transfer yönetimi")
public class BankTransferController {

    private final BankTransferService bankTransferService;

    // --- Account → Bank ---
    @GetMapping("/account-to-bank")
    @Operation(summary = "Hesaptan bankaya transfer listesi")
    public Page<AccountToBankTransfer> listAccountToBank(Pageable pageable) {
        return bankTransferService.findAllAccountToBank(pageable);
    }

    @PostMapping("/account-to-bank")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Hesaptan bankaya transfer")
    @PreAuthorize("hasAuthority('finance:create')")
    public AccountToBankTransfer createAccountToBank(@RequestBody AccountToBankTransfer transfer) {
        return bankTransferService.saveAccountToBank(transfer);
    }

    // --- Bank → Account ---
    @GetMapping("/bank-to-account")
    @Operation(summary = "Bankadan hesaba transfer listesi")
    public Page<BankToAccountTransfer> listBankToAccount(Pageable pageable) {
        return bankTransferService.findAllBankToAccount(pageable);
    }

    @PostMapping("/bank-to-account")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Bankadan hesaba transfer")
    @PreAuthorize("hasAuthority('finance:create')")
    public BankToAccountTransfer createBankToAccount(@RequestBody BankToAccountTransfer transfer) {
        return bankTransferService.saveBankToAccount(transfer);
    }

    // --- Bank → Bank ---
    @GetMapping("/bank-to-bank")
    @Operation(summary = "Bankalar arası transfer listesi")
    public Page<BankToBankTransfer> listBankToBank(Pageable pageable) {
        return bankTransferService.findAllBankToBank(pageable);
    }

    @PostMapping("/bank-to-bank")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Bankalar arası transfer")
    @PreAuthorize("hasAuthority('finance:create')")
    public BankToBankTransfer createBankToBank(@RequestBody BankToBankTransfer transfer) {
        return bankTransferService.saveBankToBank(transfer);
    }

    // --- Bank → Contact ---
    @GetMapping("/bank-to-contact")
    @Operation(summary = "Bankadan cariye transfer listesi")
    public Page<BankToContactTransfer> listBankToContact(Pageable pageable) {
        return bankTransferService.findAllBankToContact(pageable);
    }

    @PostMapping("/bank-to-contact")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Bankadan cariye transfer")
    @PreAuthorize("hasAuthority('finance:create')")
    public BankToContactTransfer createBankToContact(@RequestBody BankToContactTransfer transfer) {
        return bankTransferService.saveBankToContact(transfer);
    }

    // --- Contact → Bank ---
    @GetMapping("/contact-to-bank")
    @Operation(summary = "Cariden bankaya transfer listesi")
    public Page<ContactToBankTransfer> listContactToBank(Pageable pageable) {
        return bankTransferService.findAllContactToBank(pageable);
    }

    @PostMapping("/contact-to-bank")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cariden bankaya transfer")
    @PreAuthorize("hasAuthority('finance:create')")
    public ContactToBankTransfer createContactToBank(@RequestBody ContactToBankTransfer transfer) {
        return bankTransferService.saveContactToBank(transfer);
    }
}

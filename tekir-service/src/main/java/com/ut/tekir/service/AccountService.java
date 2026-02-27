package com.ut.tekir.service;

import com.ut.tekir.common.dto.account.AccountDetailDTO;
import com.ut.tekir.common.dto.account.AccountFilterModel;
import com.ut.tekir.common.dto.account.AccountListDTO;
import com.ut.tekir.common.dto.account.AccountSaveRequest;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.AccountTxn;
import com.ut.tekir.common.entity.Organization;
import com.ut.tekir.common.enums.AccountType;
import com.ut.tekir.repository.AccountRepository;
import com.ut.tekir.repository.AccountTxnRepository;
import com.ut.tekir.repository.OrganizationRepository;
import com.ut.tekir.service.mapper.AccountMapper;
import com.ut.tekir.service.spec.AccountSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountTxnRepository accountTxnRepository;
    private final OrganizationRepository organizationRepository;
    private final AccountMapper accountMapper;

    public Page<AccountListDTO> search(AccountFilterModel filter, Pageable pageable) {
        return accountRepository.findAll(AccountSpecifications.maxFilter(filter), pageable)
                .map(accountMapper::toListDTO);
    }

    public AccountDetailDTO get(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
        return accountMapper.toDetailDTO(account);
    }

    @Transactional
    public AccountDetailDTO create(AccountSaveRequest request) {
        Account account = new Account();
        mapRequestToEntity(request, account);
        account = accountRepository.save(account);
        return get(account.getId());
    }

    @Transactional
    public AccountDetailDTO update(Long id, AccountSaveRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
        mapRequestToEntity(request, account);
        account = accountRepository.save(account);
        return get(account.getId());
    }

    @Transactional
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<com.ut.tekir.common.dto.SuggestDTO> suggest(String query) {
        return accountRepository.findAll((root, q, cb) ->
            cb.or(
                cb.like(cb.lower(root.get("code")), query.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name")), "%" + query.toLowerCase() + "%")
            ), Pageable.ofSize(10))
            .map(a -> new com.ut.tekir.common.dto.SuggestDTO(a.getId(), a.getCode(), a.getName()))
            .getContent();
    }
    
    // --- Transactions ---

    public List<AccountTxn> getTransactions(Long accountId) {
        return accountTxnRepository.findByAccount_Id(accountId);
    }

    @Transactional
    public void saveTxn(AccountTxn txn) {
        accountTxnRepository.save(txn);
    }

    private void mapRequestToEntity(AccountSaveRequest request, Account account) {
        account.setCode(request.code());
        account.setName(request.name());
        account.setInfo(request.info());
        account.setCurrency(request.currency());
        
        if (request.accountType() != null) {
            account.setAccountType(AccountType.valueOf(request.accountType()));
        }
        
        if (request.organizationId() != null) {
            Organization org = organizationRepository.getReferenceById(request.organizationId());
            account.setOrganization(org);
        } else {
            account.setOrganization(null);
        }
    }
}


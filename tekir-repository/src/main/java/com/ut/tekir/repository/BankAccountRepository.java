package com.ut.tekir.repository;

import com.ut.tekir.common.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    java.util.List<BankAccount> findByBankBranch(com.ut.tekir.common.entity.BankBranch bankBranch);
    java.util.List<BankAccount> findByBankBranch_Bank(com.ut.tekir.common.entity.Bank bank);
}

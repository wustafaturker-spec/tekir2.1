package com.ut.tekir.repository;

import com.ut.tekir.common.entity.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
    java.util.List<BankBranch> findByBank(com.ut.tekir.common.entity.Bank bank);
}

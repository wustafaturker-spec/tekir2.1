package com.ut.tekir.repository;

import com.ut.tekir.common.entity.AccountTxn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTxnRepository extends JpaRepository<AccountTxn, Long> {
    List<AccountTxn> findByAccount_Id(Long accountId);
}

package com.ut.tekir.repository;
import com.ut.tekir.common.entity.AccountCreditNoteCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface AccountCreditNoteCurrencyRateRepository extends JpaRepository<AccountCreditNoteCurrencyRate, Long>, JpaSpecificationExecutor<AccountCreditNoteCurrencyRate> {}

package com.ut.tekir.repository;
import com.ut.tekir.common.entity.AccountCreditNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface AccountCreditNoteRepository extends JpaRepository<AccountCreditNote, Long>, JpaSpecificationExecutor<AccountCreditNote> {}

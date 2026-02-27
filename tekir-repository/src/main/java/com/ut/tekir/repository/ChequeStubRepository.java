package com.ut.tekir.repository;
import com.ut.tekir.common.entity.ChequeStub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository public interface ChequeStubRepository extends JpaRepository<ChequeStub, Long>, JpaSpecificationExecutor<ChequeStub> {}

package com.ut.tekir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactAddress;

@Repository
public interface ContactAddressRepository extends JpaRepository<ContactAddress, Long> {
    List<ContactAddress> findByOwner(Contact owner);
    List<ContactAddress> findByOwnerAndActiveAddressTrue(Contact owner);
    List<ContactAddress> findByOwnerIdOrderByDefaultAddressDescActiveAddressDesc(Long ownerId);
}

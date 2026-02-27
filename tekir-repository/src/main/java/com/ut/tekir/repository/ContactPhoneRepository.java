package com.ut.tekir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.ContactPhone;

@Repository
public interface ContactPhoneRepository extends JpaRepository<ContactPhone, Long> {
    List<ContactPhone> findByOwnerIdOrderByDefaultPhoneDescActivePhoneDesc(Long ownerId);
}

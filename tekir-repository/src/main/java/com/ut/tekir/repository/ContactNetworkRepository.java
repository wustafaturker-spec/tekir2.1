package com.ut.tekir.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ut.tekir.common.entity.ContactNetwork;

@Repository
public interface ContactNetworkRepository extends JpaRepository<ContactNetwork, Long> {
    List<ContactNetwork> findByOwnerIdOrderByDefaultNetworkDescActiveNetworkDesc(Long ownerId);
}

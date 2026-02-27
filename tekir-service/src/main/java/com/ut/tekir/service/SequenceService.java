package com.ut.tekir.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Thread.subtract(safe) sequence number generator.
 * Replaces legacy SequenceManagerBean (which had SQL injection vulnerability).
 *
 * Uses the existing hibernate_sequences table for backward compatibility.
 */
@Service
public class SequenceService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Generates a new sequential number for the given serial key.
     * Uses REQUIRES_NEW propagation to avoid sequence gaps in case of rollback.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized String getNewNumber(String serial, int length) {
        // Try to find existing sequence
        var results = entityManager
            .createNativeQuery("SELECT sequence_next_hi_value FROM hibernate_sequences WHERE sequence_name = :serial")
            .setParameter("serial", serial)
            .getResultList();

        long nextValue;
        if (results.isEmpty()) {
            nextValue = 1L;
            entityManager.createNativeQuery(
                "INSERT INTO hibernate_sequences (sequence_name, sequence_next_hi_value) VALUES (:serial, :val)")
                .setParameter("serial", serial)
                .setParameter("val", nextValue)
                .executeUpdate();
        } else {
            nextValue = ((Number) results.get(0)).longValue() + 1;
            entityManager.createNativeQuery(
                "UPDATE hibernate_sequences SET sequence_next_hi_value = :val WHERE sequence_name = :serial")
                .setParameter("val", nextValue)
                .setParameter("serial", serial)
                .executeUpdate();
        }

        return String.format("%0" + length + "d", nextValue);
    }

    /**
     * Generates a serial number with prefix from options.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized String getNewSerialNumber(String key, String serialPrefix) {
        return getNewNumber(key + "." + serialPrefix, 6);
    }
}


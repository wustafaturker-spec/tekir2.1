package com.ut.tekir.tenant.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Seeds default data for a new tenant: currencies, tax rates, units, default
 * warehouse, default cash account.
 */
@Slf4j
@Service
public class TenantSeedDataService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void seedDefaultData(Long tenantId) {
        String tid = tenantId.toString();
        log.info("Seeding default data for tenant: {}", tenantId);

        seedCurrencies(tid);
        seedTaxRates(tid);
        seedUnits(tid);
        seedDefaultCashAccount(tid);
        seedDefaultWarehouse(tid);

        log.info("Default data seeded successfully for tenant: {}", tenantId);
    }

    private void seedCurrencies(String tenantId) {
        // Insert default currencies into CURRENCIES table
        executeIfTableExists("CURRENCIES",
                "INSERT INTO CURRENCIES (CODE, NAME, SYMBOL, ACTIVE, TENANT_ID, CREATE_DATE, CREATE_USER) VALUES " +
                        "('TL', 'Turk Lirasi', '₺', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('USD', 'Amerikan Dolari', '$', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('EUR', 'Euro', '€', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('GBP', 'Ingiliz Sterlini', '£', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system') " +
                        "ON CONFLICT DO NOTHING");
    }

    private void seedTaxRates(String tenantId) {
        executeIfTableExists("TAX",
                "INSERT INTO TAX (CODE, NAME, RATE, ACTIVE, TENANT_ID, CREATE_DATE, CREATE_USER) VALUES " +
                        "('KDV18', 'KDV %18', 18.0, true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('KDV10', 'KDV %10', 10.0, true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('KDV1', 'KDV %1', 1.0, true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('KDV20', 'KDV %20', 20.0, true, " + tenantId + ", CURRENT_TIMESTAMP, 'system') " +
                        "ON CONFLICT DO NOTHING");
    }

    private void seedUnits(String tenantId) {
        executeIfTableExists("UNIT",
                "INSERT INTO UNIT (CODE, NAME, ACTIVE, TENANT_ID, CREATE_DATE, CREATE_USER) VALUES " +
                        "('ADET', 'Adet', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('KG', 'Kilogram', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('LT', 'Litre', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system'), " +
                        "('MT', 'Metre', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system') " +
                        "ON CONFLICT DO NOTHING");
    }

    private void seedDefaultCashAccount(String tenantId) {
        executeIfTableExists("ACCOUNT",
                "INSERT INTO ACCOUNT (CODE, NAME, INFO, TENANT_ID, CREATE_DATE, CREATE_USER) VALUES "
                        +
                        "('KASA01', 'Ana Kasa', 'Cashbook', " + tenantId + ", CURRENT_TIMESTAMP, 'system') " +
                        "ON CONFLICT DO NOTHING");
    }

    private void seedDefaultWarehouse(String tenantId) {
        executeIfTableExists("WAREHOUSE",
                "INSERT INTO WAREHOUSE (CODE, NAME, IS_ACTIVE, TENANT_ID, CREATE_DATE, CREATE_USER) VALUES " +
                        "('DEPO01', 'Ana Depo', true, " + tenantId + ", CURRENT_TIMESTAMP, 'system') " +
                        "ON CONFLICT DO NOTHING");
    }

    private void executeIfTableExists(String tableName, String sql) {
        try {
            entityManager.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            log.warn("Seed data skipped for table {} (may not exist yet): {}", tableName, e.getMessage());
        }
    }
}

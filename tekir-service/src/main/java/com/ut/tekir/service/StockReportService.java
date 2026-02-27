package com.ut.tekir.service;

import com.ut.tekir.common.dto.stock.StockMovementDetailDTO;
import com.ut.tekir.common.dto.stock.StockStatusDTO;
import com.ut.tekir.tenant.context.TenantContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class StockReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<StockStatusDTO> getStockStatus(String search) {
        String tenantId = TenantContext.getTenantIdAsString();

        String sql =
            "SELECT p.ID, p.CODE, p.NAME, p.UNIT, " +
            "  COALESCE(SUM(CASE WHEN pt.FINANCE_ACTION = 0 THEN pt.QUANTITY ELSE 0 END), 0) AS totalIn, " +
            "  COALESCE(SUM(CASE WHEN pt.FINANCE_ACTION = 1 THEN pt.QUANTITY ELSE 0 END), 0) AS totalOut, " +
            "  COALESCE(SUM(CASE WHEN pt.FINANCE_ACTION = 0 THEN pt.QUANTITY ELSE -pt.QUANTITY END), 0) AS balance " +
            "FROM PRODUCT p " +
            "LEFT JOIN PRODUCT_TXN pt ON pt.PRODUCT_ID = p.ID AND pt.TENANT_ID = :tenantId " +
            "WHERE p.TENANT_ID = :tenantId " +
            (search != null && !search.isBlank()
                ? "AND (LOWER(p.CODE) LIKE :search OR LOWER(p.NAME) LIKE :search) " : "") +
            "GROUP BY p.ID, p.CODE, p.NAME, p.UNIT " +
            "ORDER BY p.CODE";

        var query = entityManager.createNativeQuery(sql)
                .setParameter("tenantId", tenantId);

        if (search != null && !search.isBlank()) {
            query.setParameter("search", "%" + search.toLowerCase() + "%");
        }

        List<Object[]> rows = query.getResultList();

        return rows.stream().map(row -> new StockStatusDTO(
                ((Number) row[0]).longValue(),
                (String) row[1],
                (String) row[2],
                (String) row[3],
                toBD(row[4]),
                toBD(row[5]),
                toBD(row[6])
        )).toList();
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<StockMovementDetailDTO> getMovements(Long productId) {
        String tenantId = TenantContext.getTenantIdAsString();

        String sql =
            "SELECT pt.ID, pt.TXN_DATE, pt.FINANCE_ACTION, pt.DOCUMENT_TYPE, pt.DOCUMENT_ID, " +
            "  pt.QUANTITY, pt.UNIT, pt.UNIT_PRICE, pt.REFERENCE, pt.INFO, pt.SERIAL, " +
            "  w.NAME AS warehouseName, " +
            "  CASE " +
            "    WHEN pt.DOCUMENT_TYPE IN (3,4,5,6,47,48,52,53,69,75) " +
            "      THEN (SELECT c.NAME FROM CONTACT c JOIN INVOICE i ON i.CONTACT_ID = c.ID " +
            "            WHERE i.ID = pt.DOCUMENT_ID AND i.TENANT_ID = :tenantId LIMIT 1) " +
            "    WHEN pt.DOCUMENT_TYPE IN (9,10) " +
            "      THEN (SELECT c.NAME FROM CONTACT c JOIN SHIPMENT_NOTE sn ON sn.CONTACT_ID = c.ID " +
            "            WHERE sn.ID = pt.DOCUMENT_ID AND sn.TENANT_ID = :tenantId LIMIT 1) " +
            "    ELSE NULL " +
            "  END AS contactName, " +
            "  CASE " +
            "    WHEN pt.DOCUMENT_TYPE IN (3,4,5,6,47,48,52,53,69,75) " +
            "      THEN (SELECT i.CODE FROM INVOICE i " +
            "            WHERE i.ID = pt.DOCUMENT_ID AND i.TENANT_ID = :tenantId LIMIT 1) " +
            "    WHEN pt.DOCUMENT_TYPE IN (9,10) " +
            "      THEN (SELECT sn.CODE FROM SHIPMENT_NOTE sn " +
            "            WHERE sn.ID = pt.DOCUMENT_ID AND sn.TENANT_ID = :tenantId LIMIT 1) " +
            "    WHEN pt.DOCUMENT_TYPE = 11 " +
            "      THEN (SELECT tr.CODE FROM PRODUCT_TRANSFER tr " +
            "            WHERE tr.ID = pt.DOCUMENT_ID AND tr.TENANT_ID = :tenantId LIMIT 1) " +
            "    WHEN pt.DOCUMENT_TYPE = 13 " +
            "      THEN (SELECT v.CODE FROM PRODUCT_VIREMENT v " +
            "            WHERE v.ID = pt.DOCUMENT_ID AND v.TENANT_ID = :tenantId LIMIT 1) " +
            "    ELSE pt.REFERENCE " +
            "  END AS documentCode " +
            "FROM PRODUCT_TXN pt " +
            "LEFT JOIN WAREHOUSE w ON w.ID = pt.WAREHOUSE_ID " +
            "WHERE pt.PRODUCT_ID = :productId AND pt.TENANT_ID = :tenantId " +
            "ORDER BY pt.TXN_DATE DESC";

        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("tenantId", tenantId)
                .setParameter("productId", productId)
                .getResultList();

        return rows.stream().map(row -> new StockMovementDetailDTO(
                ((Number) row[0]).longValue(),
                toDate(row[1]),
                ((Number) row[2]).intValue() == 0 ? "Giriş" : "Çıkış",
                docTypeLabel(row[3] != null ? ((Number) row[3]).intValue() : 0),
                row[4] != null ? ((Number) row[4]).longValue() : null,
                (String) row[13],  // documentCode
                (String) row[12],  // contactName
                (String) row[11],  // warehouseName
                toBD(row[5]),
                (String) row[6],
                toBD(row[7]),
                (String) row[8],
                (String) row[9]
        )).toList();
    }

    private BigDecimal toBD(Object v) {
        if (v == null) return BigDecimal.ZERO;
        return new BigDecimal(v.toString());
    }

    private LocalDate toDate(Object v) {
        if (v == null) return null;
        if (v instanceof java.sql.Date d) return d.toLocalDate();
        if (v instanceof LocalDate d) return d;
        return null;
    }

    private String docTypeLabel(int ordinal) {
        return switch (ordinal) {
            case 3  -> "Alış Faturası";
            case 4  -> "Satış Faturası";
            case 5  -> "İrs. Alış Faturası";
            case 6  -> "İrs. Satış Faturası";
            case 9  -> "Alış İrsaliyesi";
            case 10 -> "Satış İrsaliyesi";
            case 11 -> "Depo Transferi";
            case 12 -> "Masraf Fişi";
            case 13 -> "Virman";
            case 47 -> "Nakliye Faturası";
            case 52 -> "D.Varlık Alış";
            case 53 -> "D.Varlık Satış";
            case 69 -> "Perakende Satış";
            case 75 -> "İade Faturası";
            case 77 -> "Sayım Fişi";
            default -> "Belge (" + ordinal + ")";
        };
    }
}

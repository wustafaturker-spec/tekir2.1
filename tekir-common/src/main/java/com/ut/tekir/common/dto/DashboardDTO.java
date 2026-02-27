package com.ut.tekir.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DashboardDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal totalSales;
    private Long pendingOrders;
    private BigDecimal cashBalance;
    private BigDecimal pendingPayments; // Bekleyen ödemeler (bizim ödeyeceğimiz)
    private BigDecimal pendingCollections; // Bekleyen tahsilatlar (bize ödenecek)

    private List<RecentTransactionDTO> recentTransactions;

    @Data
    public static class RecentTransactionDTO implements Serializable {
        private String id;
        private LocalDate date;
        private String contactName;
        private BigDecimal amount;
        private String type; // Satış, Tahsilat, Ödeme vb.
        private String status;
        private String currency;
    }
}

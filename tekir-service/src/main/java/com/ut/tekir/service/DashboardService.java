package com.ut.tekir.service;

import com.ut.tekir.common.dto.DashboardDTO;
import com.ut.tekir.common.dto.DashboardDTO.RecentTransactionDTO;
import com.ut.tekir.repository.InvoiceRepository;
// import com.ut.tekir.repository.OrderRepository;
// import com.ut.tekir.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InvoiceRepository invoiceRepository;
    // private final OrderRepository orderRepository; // Assuming OrderRepository exists
    // private final AccountRepository accountRepository; // Assuming AccountRepository exists for cash balance

    @Transactional(readOnly = true)
    public DashboardDTO getDashboardMetrics() {
        DashboardDTO dto = new DashboardDTO();

        // Mock data for now until we have robust queries or specifications
        // In a real scenario, we would have specific repository methods like findTotalSales()
        
        dto.setTotalSales(BigDecimal.valueOf(125430.50));
        dto.setPendingOrders(24L);
        dto.setCashBalance(BigDecimal.valueOf(45200.00));
        dto.setPendingPayments(BigDecimal.valueOf(12000));
        dto.setPendingCollections(BigDecimal.valueOf(8500));

        List<RecentTransactionDTO> transactions = new ArrayList<>();
        
        RecentTransactionDTO t1 = new RecentTransactionDTO();
        t1.setId("1");
        t1.setDate(java.time.LocalDate.now());
        t1.setContactName("ABC Ltd.");
        t1.setAmount(BigDecimal.valueOf(5400));
        t1.setType("Satış Faturası");
        t1.setStatus("Ödendi");
        t1.setCurrency("TRY");
        transactions.add(t1);

        RecentTransactionDTO t2 = new RecentTransactionDTO();
        t2.setId("2");
        t2.setDate(java.time.LocalDate.now().minusDays(1));
        t2.setContactName("XYZ A.Ş.");
        t2.setAmount(BigDecimal.valueOf(1200));
        t2.setType("Tahsilat");
        t2.setStatus("Tamamlandı");
        t2.setCurrency("TRY");
        transactions.add(t2);

        dto.setRecentTransactions(transactions);

        return dto;
    }
}

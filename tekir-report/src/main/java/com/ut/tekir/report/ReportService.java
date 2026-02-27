package com.ut.tekir.report;

import com.ut.tekir.common.dto.contact.ContactFilterModel;
import com.ut.tekir.common.dto.product.ProductFilterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Domain.subtract(specific) wrapper for JasperReportEngine.
 * Provides methods for specific reports like Contact List, Invoice, etc.
 */
@Service
@RequiredArgsConstructor
public class ReportService {

    private final JasperReportEngine reportEngine;

    // --- Contact Reports ---

    public byte[] generateContactListPdf(ContactFilterModel filter) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", filter.getCode());
        params.put("name", filter.getName()); // Fixed: name -> fullname
        params.put("category", filter.getCategoryId());
        // Add other filter params as needed for the report

        return reportEngine.exportToPdf("cari_listesi", params);
    }

    public byte[] generateContactListXls(ContactFilterModel filter) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", filter.getCode());
        params.put("name", filter.getName()); // Fixed: name -> fullname

        return reportEngine.exportToXls("cari_listesi_excel", params);
    }

    public byte[] generateContactDetailPdf(Long contactId) {
        Map<String, Object> params = new HashMap<>();
        params.put("contactId", contactId);

        return reportEngine.exportToPdf("cari_detay", params);
    }

    // --- Product Reports ---

    public byte[] generateProductListPdf(ProductFilterModel filter) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", filter.getCode());
        params.put("name", filter.getName()); // ProductFilterModel has 'name'

        return reportEngine.exportToPdf("urun_listesi", params);
    }

    public byte[] generateStockStatusPdf() {
        return reportEngine.exportToPdf("depostok_durum", new HashMap<>());
    }

    // --- Invoice Reports ---

    public byte[] generateInvoicePdf(Long invoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invoiceId", invoiceId);

        return reportEngine.exportToPdf("alis-satis-fatura", params);
    }

    public byte[] generateInvoiceListPdf(Long contactId, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("contactId", contactId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return reportEngine.exportToPdf("fatura_listesi", params);
    }

    public byte[] generateInvoiceListXls(Long contactId, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("contactId", contactId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return reportEngine.exportToXls("fatura_listesi_excel", params);
    }

    public byte[] generateSalesInvoicePdf(Long invoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invoiceId", invoiceId);

        return reportEngine.exportToPdf("satis_faturasi", params);
    }

    // --- Other Reports ---

    public byte[] generatePurchaseInvoicePdf(Long invoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invoiceId", invoiceId);
        return reportEngine.exportToPdf("alis_faturasi", params);
    }

    public byte[] generatePaymentReceiptPdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("paymentId", id);
        return reportEngine.exportToPdf("odeme_makbuzu", params);
    }

    public byte[] generateChequePayrollPdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("payrollId", id);
        return reportEngine.exportToPdf("cek_bordrosu", params);
    }

    public byte[] generateStockMovementPdf() {
        return reportEngine.exportToPdf("stok_hareket", new HashMap<>());
    }

    public byte[] generateOrderPdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        return reportEngine.exportToPdf("siparis_formu", params);
    }

    public byte[] generateShipmentPdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("shipmentId", id);
        return reportEngine.exportToPdf("irsaliye", params);
    }

    public byte[] generateExpensePdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("expenseId", id);
        return reportEngine.exportToPdf("masraf_fisi", params);
    }

    public byte[] generateCountNotePdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("pId", id);
        return reportEngine.exportToPdf("sayim_notu", params);
    }

    public byte[] generateBankTransferPdf(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("pId", id);
        return reportEngine.exportToPdf("havale", params);
    }
}

package com.ut.tekir.report;

import com.ut.tekir.common.dto.contact.ContactFilterModel;
import com.ut.tekir.common.dto.product.ProductFilterModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Consolidated report controller exposing all 9 jrxml report endpoints.
 */
@RestController("tekirReportController")
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Rapor oluşturma ve indirme")
public class ReportController {

    private final ReportService reportService;

    // ===================== FATURA (Invoice) Reports =====================

    @GetMapping("/invoice/{id}/pdf")
    @Operation(summary = "Fatura PDF (alış-satış)")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateInvoicePdf(id), "invoice_" + id);
    }

    @GetMapping("/sales-invoice/{id}/pdf")
    @Operation(summary = "Satış Faturası PDF")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<byte[]> getSalesInvoicePdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateSalesInvoicePdf(id), "sales_invoice_" + id);
    }

    @GetMapping("/invoice-list/pdf")
    @Operation(summary = "Fatura Listesi PDF")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<byte[]> getInvoiceListPdf(
            @RequestParam(required = false) Long contactId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return toPdfResponse(reportService.generateInvoiceListPdf(contactId, startDate, endDate), "fatura_listesi");
    }

    @GetMapping("/invoice-list/xls")
    @Operation(summary = "Fatura Listesi Excel")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<byte[]> getInvoiceListXls(
            @RequestParam(required = false) Long contactId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return toXlsResponse(reportService.generateInvoiceListXls(contactId, startDate, endDate), "fatura_listesi");
    }

    // ===================== CARİ (Contact) Reports =====================

    @GetMapping("/contact-list/pdf")
    @Operation(summary = "Cari Listesi PDF")
    @PreAuthorize("hasAuthority('contact:read')")
    public ResponseEntity<byte[]> getContactListPdf(ContactFilterModel filter) {
        return toPdfResponse(reportService.generateContactListPdf(filter), "cari_listesi");
    }

    @GetMapping("/contact-list/xls")
    @Operation(summary = "Cari Listesi Excel")
    @PreAuthorize("hasAuthority('contact:read')")
    public ResponseEntity<byte[]> getContactListXls(ContactFilterModel filter) {
        return toXlsResponse(reportService.generateContactListXls(filter), "cari_listesi");
    }

    @GetMapping("/contact/{id}/pdf")
    @Operation(summary = "Cari Detay PDF")
    @PreAuthorize("hasAuthority('contact:read')")
    public ResponseEntity<byte[]> getContactDetailPdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateContactDetailPdf(id), "cari_detay_" + id);
    }

    // ===================== ÜRÜN (Product) Reports =====================

    @GetMapping("/product-list/pdf")
    @Operation(summary = "Ürün Listesi PDF")
    @PreAuthorize("hasAuthority('product:read')")
    public ResponseEntity<byte[]> getProductListPdf(ProductFilterModel filter) {
        return toPdfResponse(reportService.generateProductListPdf(filter), "urun_listesi");
    }

    // ===================== STOK Reports =====================

    @GetMapping("/stock-status/pdf")
    @Operation(summary = "Depo Stok Durumu PDF")
    @PreAuthorize("hasAuthority('stock:read')")
    public ResponseEntity<byte[]> getStockStatusPdf() {
        return toPdfResponse(reportService.generateStockStatusPdf(), "depostok_durum");
    }

    // ===================== Helper Methods =====================

    private ResponseEntity<byte[]> toPdfResponse(byte[] content, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename + ".pdf");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    // ===================== OTHER Reports =====================

    @GetMapping("/purchase-invoice/{id}/pdf")
    @Operation(summary = "Alış Faturası PDF")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<byte[]> getPurchaseInvoicePdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generatePurchaseInvoicePdf(id), "alis_faturasi_" + id);
    }

    @GetMapping("/payment-receipt/{id}/pdf")
    @Operation(summary = "Ödeme/Tahsilat Makbuzu PDF")
    @PreAuthorize("hasAuthority('finance:read')")
    public ResponseEntity<byte[]> getPaymentReceiptPdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generatePaymentReceiptPdf(id), "makbuz_" + id);
    }

    @GetMapping("/cheque-payroll/{id}/pdf")
    @Operation(summary = "Çek Bordrosu PDF")
    @PreAuthorize("hasAuthority('cheque:read')")
    public ResponseEntity<byte[]> getChequePayrollPdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateChequePayrollPdf(id), "cek_bordrosu_" + id);
    }

    @GetMapping("/stock-movement/pdf")
    @Operation(summary = "Stok Hareket Raporu PDF")
    @PreAuthorize("hasAuthority('stock:read')")
    public ResponseEntity<byte[]> getStockMovementPdf() {
        return toPdfResponse(reportService.generateStockMovementPdf(), "stok_hareket");
    }

    @GetMapping("/order/{id}/pdf")
    @Operation(summary = "Sipariş Formu PDF")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<byte[]> getOrderPdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateOrderPdf(id), "siparis_" + id);
    }

    @GetMapping("/shipment/{id}/pdf")
    @Operation(summary = "İrsaliye PDF")
    @PreAuthorize("hasAuthority('stock:read')")
    public ResponseEntity<byte[]> getShipmentPdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateShipmentPdf(id), "irsaliye_" + id);
    }

    @GetMapping("/expense/{id}/pdf")
    @Operation(summary = "Masraf Fişi PDF")
    @PreAuthorize("hasAuthority('expense:read')")
    public ResponseEntity<byte[]> getExpensePdf(@PathVariable Long id) {
        return toPdfResponse(reportService.generateExpensePdf(id), "masraf_fisi_" + id);
    }

    private ResponseEntity<byte[]> toXlsResponse(byte[] content, String filename) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData("attachment", filename + ".xls");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}

package com.ut.tekir.api.controller;

import com.ut.tekir.common.dto.contact.ContactFilterModel;
import com.ut.tekir.common.dto.product.ProductFilterModel;
import com.ut.tekir.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Raporlama işlemleri")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/contact-list")
    @Operation(summary = "Cari listesi raporu (PDF/XLS)")
    public ResponseEntity<byte[]> contactList(ContactFilterModel filter, @RequestParam(defaultValue = "pdf") String format) {
        byte[] content = "xls".equalsIgnoreCase(format) ?
                reportService.generateContactListXls(filter) :
                reportService.generateContactListPdf(filter);
        return ok(content, "cari_listesi." + format, format);
    }

    @GetMapping("/contact-detail/{id}")
    @Operation(summary = "Cari ekstre (PDF)")
    public ResponseEntity<byte[]> contactDetail(@PathVariable Long id) {
        return ok(reportService.generateContactDetailPdf(id), "cari_ekstre.pdf", "pdf");
    }

    @GetMapping("/product-list")
    @Operation(summary = "Ürün listesi raporu (PDF)")
    public ResponseEntity<byte[]> productList(ProductFilterModel filter) {
        return ok(reportService.generateProductListPdf(filter), "urun_listesi.pdf", "pdf");
    }

    @GetMapping("/stock-status")
    @Operation(summary = "Stok durum raporu (PDF)")
    public ResponseEntity<byte[]> stockStatus() {
        return ok(reportService.generateStockStatusPdf(), "stok_durum.pdf", "pdf");
    }

    @GetMapping("/invoice/{id}")
    @Operation(summary = "Fatura yazdır (PDF)")
    public ResponseEntity<byte[]> invoice(@PathVariable Long id) {
        return ok(reportService.generateInvoicePdf(id), "fatura_" + id + ".pdf", "pdf");
    }

    @GetMapping("/count-note/{id}")
    @Operation(summary = "Sayım Fişi yazdır (PDF)")
    public ResponseEntity<byte[]> countNote(@PathVariable Long id) {
        return ok(reportService.generateCountNotePdf(id), "sayim_" + id + ".pdf", "pdf");
    }

    @GetMapping("/bank-transfer/{id}")
    @Operation(summary = "Banka Havale/Virman yazdır (PDF)")
    public ResponseEntity<byte[]> bankTransfer(@PathVariable Long id) {
        return ok(reportService.generateBankTransferPdf(id), "havale_" + id + ".pdf", "pdf");
    }

    private ResponseEntity<byte[]> ok(byte[] content, String filename, String format) {
        MediaType mediaType = "xls".equalsIgnoreCase(format) ?
                MediaType.APPLICATION_OCTET_STREAM :
                MediaType.APPLICATION_PDF;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mediaType.toString())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(content);
    }
}



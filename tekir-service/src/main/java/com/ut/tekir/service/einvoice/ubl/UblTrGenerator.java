package com.ut.tekir.service.einvoice.ubl;

import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.EInvoiceSettings;
import com.ut.tekir.common.entity.Invoice;
import com.ut.tekir.common.entity.InvoiceItem;
import com.ut.tekir.common.enums.EDocumentType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UblTrGenerator {

    private final Configuration freemarkerConfig;

    public byte[] generateUbl(Invoice invoice, Contact contact, EInvoiceSettings settings, EDocumentType documentType) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("invoice", invoice);
            model.put("contact", contact);
            model.put("settings", settings);
            model.put("issueTime", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            model.put("invoiceTypeCode", "SATIS"); // Can be dynamic based on tradeAction

            // Tax Summary grouping
            List<TaxSummaryLine> taxSummary = calculateTaxSummary(invoice.getItems());
            model.put("taxSummary", taxSummary);

            if (documentType == EDocumentType.EARCHIVE) {
                // Add e-Archive specific properties
                List<Map<String, String>> props = new ArrayList<>();
                Map<String, String> emailProp = new HashMap<>();
                emailProp.put("key", "EMAIL");
                String email = contact.getNetworkList().stream()
                        .filter(n -> Boolean.TRUE.equals(n.getEmail()))
                        .map(com.ut.tekir.common.entity.ContactNetwork::getValue)
                        .findFirst()
                        .orElse("");
                emailProp.put("value", email);
                props.add(emailProp);
                model.put("eArchiveProperties", props);
            }

            String templatePath = documentType == EDocumentType.EINVOICE
                    ? "einvoice/ubl_invoice.ftl"
                    : "einvoice/ubl_earchive.ftl";

            Template template = freemarkerConfig.getTemplate(templatePath);
            String xml = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            return xml.getBytes("UTF-8");
        } catch (Exception e) {
            log.error("Error generating UBL-TR XML for invoice {}: {}", invoice.getId(), e.getMessage());
            throw new RuntimeException("UBL-TR XML üretilemedi: " + e.getMessage(), e);
        }
    }

    private List<TaxSummaryLine> calculateTaxSummary(List<InvoiceItem> items) {
        Map<BigDecimal, TaxSummaryLine> summary = new HashMap<>();

        for (InvoiceItem item : items) {
            BigDecimal rate = item.getTax().getRate();
            if (rate == null)
                rate = BigDecimal.ZERO;

            TaxSummaryLine line = summary.getOrDefault(rate, new TaxSummaryLine(rate));
            line.add(item.getAmount().getValue(), item.getTaxAmount().getValue());
            summary.put(rate, line);
        }

        return new ArrayList<>(summary.values());
    }

    public static class TaxSummaryLine {
        public BigDecimal taxRate;
        public BigDecimal taxableAmount = BigDecimal.ZERO;
        public BigDecimal taxAmount = BigDecimal.ZERO;

        public TaxSummaryLine(BigDecimal taxRate) {
            this.taxRate = taxRate;
        }

        public void add(BigDecimal taxable, BigDecimal tax) {
            this.taxableAmount = this.taxableAmount.add(taxable != null ? taxable : BigDecimal.ZERO);
            this.taxAmount = this.taxAmount.add(tax != null ? tax : BigDecimal.ZERO);
        }

        public BigDecimal getTaxRate() {
            return taxRate;
        }

        public BigDecimal getTaxableAmount() {
            return taxableAmount;
        }

        public BigDecimal getTaxAmount() {
            return taxAmount;
        }
    }
}

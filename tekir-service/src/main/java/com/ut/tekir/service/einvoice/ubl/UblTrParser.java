package com.ut.tekir.service.einvoice.ubl;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.ut.tekir.service.einvoice.dto.InboundInvoiceDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Service to parse UBL-TR XML into an InboundInvoiceDTO.
 * Uses XPath to easily extract required fields without complex JAXB bindings.
 */
@Slf4j
@Service
public class UblTrParser {

    /**
     * Parses a raw UBL-TR XML byte array into an InboundInvoiceDTO.
     */
    public InboundInvoiceDTO parse(byte[] xmlData, String envelopeId) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Important: namespace awareness is needed for UBL, but can sometimes
            // complicate XPath.
            // For simple extraction, ignoring namespaces in XPath or using local-name() is
            // safer.
            factory.setNamespaceAware(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlData));

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            // Extract basic invoice details
            String uuid = extractString(xpath, doc, "//*[local-name()='UUID']");
            String invoiceNumber = extractString(xpath, doc, "//*[local-name()='ID']");
            String profileId = extractString(xpath, doc, "//*[local-name()='ProfileID']");
            String invoiceTypeCode = extractString(xpath, doc, "//*[local-name()='InvoiceTypeCode']");

            String issueDateStr = extractString(xpath, doc, "//*[local-name()='IssueDate']");
            LocalDate date = LocalDate.now();
            if (issueDateStr != null && !issueDateStr.isEmpty()) {
                date = LocalDate.parse(issueDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            // Extract Sender Info (AccountingSupplierParty)
            String senderVkn = extractString(xpath, doc,
                    "//*[local-name()='AccountingSupplierParty']//*[local-name()='PartyTaxScheme']//*[local-name()='CompanyID']");

            // Try to extract title (PartyName or Person name)
            String senderTitle = extractString(xpath, doc,
                    "//*[local-name()='AccountingSupplierParty']//*[local-name()='PartyName']//*[local-name()='Name']");
            if (senderTitle == null || senderTitle.trim().isEmpty()) {
                String firstName = extractString(xpath, doc,
                        "//*[local-name()='AccountingSupplierParty']//*[local-name()='Person']//*[local-name()='FirstName']");
                String familyName = extractString(xpath, doc,
                        "//*[local-name()='AccountingSupplierParty']//*[local-name()='Person']//*[local-name()='FamilyName']");
                if (firstName != null && familyName != null) {
                    senderTitle = firstName + " " + familyName;
                }
            }

            // Extract Totals
            String payableAmountStr = extractString(xpath, doc,
                    "//*[local-name()='LegalMonetaryTotal']//*[local-name()='PayableAmount']");
            BigDecimal amount = BigDecimal.ZERO;
            if (payableAmountStr != null && !payableAmountStr.isEmpty()) {
                amount = new BigDecimal(payableAmountStr);
            }

            String currencyStr = extractAttribute(xpath, doc,
                    "//*[local-name()='LegalMonetaryTotal']//*[local-name()='PayableAmount']", "currencyID");
            if (currencyStr == null || currencyStr.isEmpty()) {
                currencyStr = "TRY";
            }

            return new InboundInvoiceDTO(
                    uuid,
                    envelopeId,
                    senderVkn,
                    senderTitle,
                    invoiceNumber,
                    date,
                    amount,
                    currencyStr,
                    profileId != null ? profileId : "TEMELFATURA",
                    invoiceTypeCode != null ? invoiceTypeCode : "SATIS",
                    "NEW" // Initial status when fetched
            );

        } catch (Exception e) {
            log.error("Failed to parse UBL-TR XML", e);
            throw new RuntimeException("Failed to parse UBL-TR XML: " + e.getMessage(), e);
        }
    }

    private String extractString(XPath xpath, Document doc, String expression) {
        try {
            Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
            if (node != null) {
                return node.getTextContent().trim();
            }
        } catch (XPathExpressionException e) {
            log.warn("XPath evaluation failed for expression: {}", expression);
        }
        return null;
    }

    private String extractAttribute(XPath xpath, Document doc, String expression, String attributeName) {
        try {
            Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
            if (node != null && node.getAttributes() != null) {
                Node attr = node.getAttributes().getNamedItem(attributeName);
                if (attr != null) {
                    return attr.getTextContent().trim();
                }
            }
        } catch (XPathExpressionException e) {
            log.warn("XPath attribute evaluation failed for expression: {}, attribute: {}", expression, attributeName);
        }
        return null;
    }
}

<?xml version="1.0" encoding="UTF-8"?>
<Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
         xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
         xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
         xmlns:ccts="urn:un:unece:uncefact:documentation:2"
         xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
         xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
         xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2"
         xmlns:ubl="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
         xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader">
    <cbc:CustomizationID>TR1.2</cbc:CustomizationID>
    <cbc:ProfileID>${invoice.eInvoiceScenario!"TEMELFATURA"}</cbc:ProfileID>
    <cbc:ID>${invoice.code}</cbc:ID>
    <cbc:CopyIndicator>false</cbc:CopyIndicator>
    <cbc:UUID>${invoice.eInvoiceUuid}</cbc:UUID>
    <cbc:IssueDate>${invoice.date}</cbc:IssueDate>
    <cbc:IssueTime>${issueTime!"12:00:00"}</cbc:IssueTime>
    <cbc:InvoiceTypeCode>${invoiceTypeCode!"SATIS"}</cbc:InvoiceTypeCode>
    <cbc:Note>${invoice.info!""}</cbc:Note>
    <cbc:DocumentCurrencyCode>${invoice.currency!"TRY"}</cbc:DocumentCurrencyCode>
    <cbc:LineCountNumeric>${invoice.items?size}</cbc:LineCountNumeric>

    <cac:AdditionalDocumentReference>
        <cbc:ID>${invoice.eInvoiceUuid}</cbc:ID>
        <cbc:IssueDate>${invoice.date}</cbc:IssueDate>
        <cbc:DocumentTypeCode>SETTLEMENT</cbc:DocumentTypeCode>
    </cac:AdditionalDocumentReference>

    <cac:Signature>
        <cbc:ID schemeID="VKN_TCKN">${settings.senderVkn}</cbc:ID>
        <cac:SignatoryParty>
            <cac:PartyIdentification>
                <cbc:ID schemeID="VKN_TCKN">${settings.senderVkn}</cbc:ID>
            </cac:PartyIdentification>
            <cac:PostalAddress>
                <cbc:StreetName>${settings.senderAddress!""}</cbc:StreetName>
                <cbc:CitySubdivisionName>${settings.senderDistrict!""}</cbc:CitySubdivisionName>
                <cbc:CityName>${settings.senderCity!""}</cbc:CityName>
                <cbc:CountrySubentity>${settings.senderProvince!""}</cbc:CountrySubentity>
                <cac:Country>
                    <cbc:Name>Türkiye</cbc:Name>
                </cac:Country>
            </cac:PostalAddress>
        </cac:SignatoryParty>
        <cac:DigitalSignatureAttachment>
            <cac:ExternalReference>
                <cbc:URI>#Signature</cbc:URI>
            </cac:ExternalReference>
        </cac:DigitalSignatureAttachment>
    </cac:Signature>

    <cac:AccountingSupplierParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="VKN_TCKN">${settings.senderVkn}</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyName>
                <cbc:Name>${settings.senderName!""}</cbc:Name>
            </cac:PartyName>
            <cac:PostalAddress>
                <cbc:StreetName>${settings.senderAddress!""}</cbc:StreetName>
                <cbc:CitySubdivisionName>${settings.senderDistrict!""}</cbc:CitySubdivisionName>
                <cbc:CityName>${settings.senderCity!""}</cbc:CityName>
                <cac:Country>
                    <cbc:Name>Türkiye</cbc:Name>
                </cac:Country>
            </cac:PostalAddress>
            <cac:PartyTaxScheme>
                <cac:TaxScheme>
                    <cbc:Name>${settings.senderTaxOffice!""}</cbc:Name>
                </cac:TaxScheme>
            </cac:PartyTaxScheme>
        </cac:Party>
    </cac:AccountingSupplierParty>

    <cac:AccountingCustomerParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="${(contact.entityType == 'CORPORATE')?string('VKN','TCKN')}">${contact.taxNumber!contact.ssn}</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyName>
                <cbc:Name>${contact.name}</cbc:Name>
            </cac:PartyName>
            <cac:PostalAddress>
                <cbc:StreetName>${contact.address!""}</cbc:StreetName>
                <cbc:CitySubdivisionName>${contact.district!""}</cbc:CitySubdivisionName>
                <cbc:CityName>${contact.city!""}</cbc:CityName>
                <cac:Country>
                    <cbc:Name>Türkiye</cbc:Name>
                </cac:Country>
            </cac:PostalAddress>
            <cac:PartyTaxScheme>
                <cac:TaxScheme>
                    <cbc:Name>${contact.taxOffice!""}</cbc:Name>
                </cac:TaxScheme>
            </cac:PartyTaxScheme>
        </cac:Party>
    </cac:AccountingCustomerParty>

    <cac:TaxTotal>
        <cbc:TaxAmount currencyID="${invoice.currency!"TRY"}">${invoice.totalTax!0}</cbc:TaxAmount>
        <#list taxSummary as ts>
        <cac:TaxSubtotal>
            <cbc:TaxableAmount currencyID="${invoice.currency!"TRY"}">${ts.taxableAmount}</cbc:TaxableAmount>
            <cbc:TaxAmount currencyID="${invoice.currency!"TRY"}">${ts.taxAmount}</cbc:TaxAmount>
            <cbc:Percent>${ts.taxRate}</cbc:Percent>
            <cac:TaxCategory>
                <cac:TaxScheme>
                    <cbc:Name>KDV</cbc:Name>
                    <cbc:TaxTypeCode>0015</cbc:TaxTypeCode>
                </cac:TaxScheme>
            </cac:TaxCategory>
        </cac:TaxSubtotal>
        </#list>
    </cac:TaxTotal>

    <cac:LegalMonetaryTotal>
        <cbc:LineExtensionAmount currencyID="${invoice.currency!"TRY"}">${invoice.totalAmount!0}</cbc:LineExtensionAmount>
        <cbc:TaxExclusiveAmount currencyID="${invoice.currency!"TRY"}">${invoice.totalAmount!0}</cbc:TaxExclusiveAmount>
        <cbc:TaxInclusiveAmount currencyID="${invoice.currency!"TRY"}">${invoice.grandTotal!0}</cbc:TaxInclusiveAmount>
        <cbc:PayableAmount currencyID="${invoice.currency!"TRY"}">${invoice.grandTotal!0}</cbc:PayableAmount>
    </cac:LegalMonetaryTotal>

    <#list invoice.items as item>
    <cac:InvoiceLine>
        <cbc:ID>${item_index + 1}</cbc:ID>
        <cbc:InvoicedQuantity unitCode="${item.unitName!"NIU"}">${item.quantity}</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="${invoice.currency!"TRY"}">${item.amount}</cbc:LineExtensionAmount>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="${invoice.currency!"TRY"}">${item.taxAmount}</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="${invoice.currency!"TRY"}">${item.amount}</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="${invoice.currency!"TRY"}">${item.taxAmount}</cbc:TaxAmount>
                <cbc:Percent>${item.taxRate}</cbc:Percent>
                <cac:TaxCategory>
                    <cac:TaxScheme>
                        <cbc:Name>KDV</cbc:Name>
                        <cbc:TaxTypeCode>0015</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        <cac:Item>
            <cbc:Description>${item.productName}</cbc:Description>
            <cbc:Name>${item.productName}</cbc:Name>
            <cac:SellersItemIdentification>
                <cbc:ID>${item.productCode!""}</cbc:ID>
            </cac:SellersItemIdentification>
        </cac:Item>
        <cac:Price>
            <cbc:PriceAmount currencyID="${invoice.currency!"TRY"}">${item.unitPrice}</cbc:PriceAmount>
        </cac:Price>
    </cac:InvoiceLine>
    </#list>
</Invoice>

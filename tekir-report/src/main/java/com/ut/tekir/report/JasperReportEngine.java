package com.ut.tekir.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * JasperReports engine wrapper.
 * Replaces legacy JasperHandlerBean from Seam framework.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JasperReportEngine {

    private final DataSource dataSource;

    public byte[] exportToPdf(String reportName, Map<String, Object> parameters) {
        try {
            JasperPrint print = fillReport(reportName, parameters);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException e) {
            throw new ReportGenerationException("Failed to generate PDF report: " + reportName, e);
        }
    }

    public byte[] exportToXls(String reportName, Map<String, Object> parameters) {
        try {
            JasperPrint print = fillReport(reportName, parameters);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

            SimpleXlsReportConfiguration config = new SimpleXlsReportConfiguration();
            config.setOnePagePerSheet(false);
            config.setDetectCellType(true);
            config.setWhitePageBackground(false);
            exporter.setConfiguration(config);

            exporter.exportReport();
            return out.toByteArray();
        } catch (JRException e) {
            throw new ReportGenerationException("Failed to generate XLS report: " + reportName, e);
        }
    }

    private JasperPrint fillReport(String reportName, Map<String, Object> parameters) throws JRException {
        // Inject Tenant ID
        String tenantId = com.ut.tekir.tenant.context.TenantContext.getTenantIdAsString();
        parameters.put("TENANT_ID", tenantId);
        
        // Set default locale to Turkish
        parameters.put(JRParameter.REPORT_LOCALE, new java.util.Locale("tr", "TR"));

        // Template Loading Strategy:
        // 1. Try tenant specific: /reports/{tenantId}/{reportName}.jrxml
        // 2. Fallback to default: /reports/{reportName}.jrxml
        
        String tenantTemplatePath = "/reports/" + tenantId + "/" + reportName + ".jrxml";
        String defaultTemplatePath = "/reports/" + reportName + ".jrxml";
        
        InputStream reportStream = getClass().getResourceAsStream(tenantTemplatePath);
        if (reportStream == null) {
            log.debug("Tenant specific report not found: {}, trying default.", tenantTemplatePath);
            reportStream = getClass().getResourceAsStream(defaultTemplatePath);
        }

        if (reportStream == null) {
            throw new ReportGenerationException("Report template not found (jrxml): " + reportName);
        }

        try (Connection connection = dataSource.getConnection()) {
            net.sf.jasperreports.engine.design.JasperDesign jasperDesign = net.sf.jasperreports.engine.xml.JRXmlLoader.load(reportStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            return JasperFillManager.fillReport(jasperReport, parameters, connection);
        } catch (SQLException e) {
            throw new ReportGenerationException("Database connection failed for report: " + reportName, e);
        }
    }
}

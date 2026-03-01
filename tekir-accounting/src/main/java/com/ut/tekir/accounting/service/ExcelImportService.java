package com.ut.tekir.accounting.service;

import com.ut.tekir.accounting.dto.AccountImportResultDTO;
import com.ut.tekir.accounting.entity.AccountPlan;
import com.ut.tekir.accounting.entity.ChartAccount;
import com.ut.tekir.accounting.enums.AccountType;
import com.ut.tekir.accounting.enums.NormalBalance;
import com.ut.tekir.accounting.repository.ChartAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelImportService {

    private final ChartAccountRepository chartAccountRepository;

    // Column indices in the import template
    private static final int COL_CODE         = 0;
    private static final int COL_NAME         = 1;
    private static final int COL_TYPE         = 2;
    private static final int COL_BALANCE      = 3;
    private static final int COL_IS_DETAIL    = 4;
    private static final int COL_DESCRIPTION  = 5;

    public AccountImportResultDTO importFromExcel(MultipartFile file, AccountPlan plan) throws IOException {
        List<AccountImportResultDTO.ImportError> errors = new ArrayList<>();
        int successCount = 0;
        int totalRows = 0;

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();

            // Collect all valid rows first (skip header row 0)
            record RowData(int rowNum, String code, String name, AccountType type,
                           NormalBalance balance, boolean isDetail, String description) {}
            List<RowData> rows = new ArrayList<>();

            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;
                totalRows++;

                String code = getCellString(row, COL_CODE);
                String name = getCellString(row, COL_NAME);

                if (code == null || code.isBlank()) {
                    errors.add(new AccountImportResultDTO.ImportError(i + 1, "", "Hesap kodu boş olamaz"));
                    continue;
                }
                if (!code.matches("^\\d{1,20}$")) {
                    errors.add(new AccountImportResultDTO.ImportError(i + 1, code, "Hesap kodu sadece rakamlardan oluşmalıdır"));
                    continue;
                }
                if (name == null || name.isBlank()) {
                    errors.add(new AccountImportResultDTO.ImportError(i + 1, code, "Hesap adı boş olamaz"));
                    continue;
                }

                AccountType type;
                try {
                    String typeStr = getCellString(row, COL_TYPE);
                    type = (typeStr != null && !typeStr.isBlank())
                            ? AccountType.valueOf(typeStr.toUpperCase())
                            : AccountType.ASSET;
                } catch (IllegalArgumentException e) {
                    errors.add(new AccountImportResultDTO.ImportError(i + 1, code,
                            "Geçersiz hesap türü. Geçerli değerler: ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE, COST"));
                    continue;
                }

                NormalBalance balance;
                try {
                    String balStr = getCellString(row, COL_BALANCE);
                    balance = (balStr != null && !balStr.isBlank())
                            ? NormalBalance.valueOf(balStr.toUpperCase())
                            : NormalBalance.DEBIT;
                } catch (IllegalArgumentException e) {
                    errors.add(new AccountImportResultDTO.ImportError(i + 1, code,
                            "Geçersiz normal bakiye. Geçerli değerler: DEBIT, CREDIT"));
                    continue;
                }

                String detailStr = getCellString(row, COL_IS_DETAIL);
                boolean isDetail = "true".equalsIgnoreCase(detailStr) || "1".equals(detailStr) || "evet".equalsIgnoreCase(detailStr);
                String description = getCellString(row, COL_DESCRIPTION);

                rows.add(new RowData(i + 1, code, name, type, balance, isDetail, description));
            }

            // Sort by code length (parents first)
            rows.sort(Comparator.comparingInt(r -> r.code().length()));

            Map<String, ChartAccount> savedByCode = new HashMap<>();

            for (RowData row : rows) {
                try {
                    if (chartAccountRepository.existsByPlanIdAndCode(plan.getId(), row.code())) {
                        errors.add(new AccountImportResultDTO.ImportError(row.rowNum(), row.code(),
                                "Bu hesap kodu bu planda zaten mevcut"));
                        continue;
                    }

                    ChartAccount account = new ChartAccount();
                    account.setPlan(plan);
                    account.setCode(row.code());
                    account.setName(row.name());
                    account.setAccountType(row.type());
                    account.setNormalBalance(row.balance());
                    account.setIsDetail(row.isDetail());
                    account.setDescription(row.description());
                    account.setIsActive(true);
                    account.setIsBlocked(false);
                    account.setLevel(computeLevel(row.code()));
                    account.setParent(findParentFromMap(row.code(), savedByCode));

                    ChartAccount saved = chartAccountRepository.save(account);
                    savedByCode.put(row.code(), saved);
                    successCount++;
                } catch (Exception e) {
                    errors.add(new AccountImportResultDTO.ImportError(row.rowNum(), row.code(),
                            "Kayıt hatası: " + e.getMessage()));
                    log.error("Error importing row {}: {}", row.rowNum(), e.getMessage());
                }
            }
        }

        return new AccountImportResultDTO(totalRows, successCount, errors.size(), errors);
    }

    public byte[] generateTemplate() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Hesap Planı");

            // Header row
            Row header = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            String[] headers = {"HESAP_KODU", "HESAP_ADI", "TUR", "NORMAL_BAKIYE", "DETAY_HESAP", "ACIKLAMA"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 5000);
            }

            // Sample rows
            Object[][] sampleData = {
                {"100", "Kasa Hesabı", "ASSET", "DEBIT", "false", ""},
                {"1000", "TL Kasası", "ASSET", "DEBIT", "true", "Türk Lirası kasa"},
                {"1001", "USD Kasası", "ASSET", "DEBIT", "true", "Dolar kasa"},
                {"320", "Satıcılar", "LIABILITY", "CREDIT", "true", ""}
            };

            for (int i = 0; i < sampleData.length; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < sampleData[i].length; j++) {
                    row.createCell(j).setCellValue(sampleData[i][j].toString());
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private int computeLevel(String code) {
        int len = code.length();
        if (len == 1) return 1;
        if (len == 2) return 2;
        if (len == 3) return 3;
        if (len <= 5) return 4;
        return 5;
    }

    private ChartAccount findParentFromMap(String code, Map<String, ChartAccount> savedByCode) {
        for (int i = code.length() - 1; i >= 1; i--) {
            String parentCode = code.substring(0, i);
            ChartAccount parent = savedByCode.get(parentCode);
            if (parent != null) return parent;
        }
        return null;
    }

    private boolean isRowEmpty(Row row) {
        for (int i = 0; i < 6; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String val = getCellString(row, i);
                if (val != null && !val.isBlank()) return false;
            }
        }
        return true;
    }

    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }
}

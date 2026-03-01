package com.ut.tekir.accounting.service;

import com.ut.tekir.accounting.dto.AccountImportResultDTO;
import com.ut.tekir.accounting.dto.AccountPlanDTO;
import com.ut.tekir.accounting.dto.AccountPlanSaveRequest;
import com.ut.tekir.accounting.entity.AccountPlan;
import com.ut.tekir.accounting.enums.AccountPlanType;
import com.ut.tekir.accounting.mapper.AccountPlanMapper;
import com.ut.tekir.accounting.repository.AccountPlanRepository;
import com.ut.tekir.accounting.repository.ChartAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountPlanService {

    private final AccountPlanRepository accountPlanRepository;
    private final ChartAccountRepository chartAccountRepository;
    private final AccountPlanMapper mapper;
    private final TdhpSeedService tdhpSeedService;
    private final ExcelImportService excelImportService;

    public List<AccountPlanDTO> listAll() {
        return accountPlanRepository.findAllByIsActiveTrueOrderByCodeAsc().stream()
                .map(mapper::toDto)
                .toList();
    }

    public AccountPlanDTO getById(Long id) {
        return mapper.toDto(findById(id));
    }

    @Transactional
    public AccountPlanDTO create(AccountPlanSaveRequest request) {
        if (accountPlanRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Bu plan kodu zaten mevcut: " + request.code());
        }

        AccountPlan plan = mapper.toEntity(request);
        if (Boolean.TRUE.equals(request.isDefault())) {
            accountPlanRepository.clearAllDefaults();
            plan.setIsDefault(true);
        }

        return mapper.toDto(accountPlanRepository.save(plan));
    }

    @Transactional
    public AccountPlanDTO update(Long id, AccountPlanSaveRequest request) {
        AccountPlan plan = findById(id);

        if (!plan.getCode().equals(request.code()) && accountPlanRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Bu plan kodu zaten mevcut: " + request.code());
        }

        mapper.updateEntity(request, plan);

        if (Boolean.TRUE.equals(request.isDefault())) {
            accountPlanRepository.clearAllDefaults();
            plan.setIsDefault(true);
        }

        return mapper.toDto(accountPlanRepository.save(plan));
    }

    @Transactional
    public void delete(Long id) {
        AccountPlan plan = findById(id);
        long count = chartAccountRepository.countByPlanId(id);
        if (count > 0) {
            throw new IllegalStateException("Hesap kodları olan bir plan silinemez (" + count + " hesap mevcut)");
        }
        accountPlanRepository.delete(plan);
    }

    @Transactional
    public AccountPlanDTO activate(Long id) {
        accountPlanRepository.clearAllDefaults();
        AccountPlan plan = findById(id);
        plan.setIsDefault(true);
        return mapper.toDto(accountPlanRepository.save(plan));
    }

    @Transactional
    public AccountPlanDTO loadTdhp() {
        String tdhpCode = "TDHP-" + LocalDate.now().getYear();

        // If plan already exists, force-delete accounts + plan, then re-seed
        accountPlanRepository.findByCode(tdhpCode).ifPresent(existing -> {
            log.info("Mevcut TDHP planı siliniyor: {} ({} hesap)", tdhpCode, existing.getAccountCount());
            chartAccountRepository.clearParentRefs(existing.getId());
            chartAccountRepository.deleteByPlanId(existing.getId());
            accountPlanRepository.delete(existing);
        });

        AccountPlan plan = new AccountPlan();
        plan.setCode(tdhpCode);
        plan.setName("Türk Tekdüzen Hesap Planı " + LocalDate.now().getYear());
        plan.setDescription("Türkiye Muhasebe Standartları kapsamında Tekdüzen Hesap Planı (TDHP)");
        plan.setPlanType(AccountPlanType.STANDARD_TDHP);
        plan.setEffectiveDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        plan.setIsActive(true);

        // Make it default if no default exists
        if (accountPlanRepository.findByIsDefaultTrue().isEmpty()) {
            plan.setIsDefault(true);
        }

        AccountPlan saved = accountPlanRepository.save(plan);
        tdhpSeedService.seedIntoplan(saved);

        // Update account count
        long count = chartAccountRepository.countByPlanId(saved.getId());
        saved.setAccountCount((int) count);
        accountPlanRepository.save(saved);

        log.info("TDHP plan loaded: {} with {} accounts", saved.getCode(), count);
        return mapper.toDto(saved);
    }

    @Transactional
    public AccountImportResultDTO importFromExcel(Long planId, MultipartFile file) throws IOException {
        AccountPlan plan = findById(planId);
        AccountImportResultDTO result = excelImportService.importFromExcel(file, plan);

        // Update account count
        long count = chartAccountRepository.countByPlanId(planId);
        plan.setAccountCount((int) count);
        accountPlanRepository.save(plan);

        return result;
    }

    public byte[] getImportTemplate() throws IOException {
        return excelImportService.generateTemplate();
    }

    private AccountPlan findById(Long id) {
        return accountPlanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hesap planı bulunamadı: " + id));
    }
}

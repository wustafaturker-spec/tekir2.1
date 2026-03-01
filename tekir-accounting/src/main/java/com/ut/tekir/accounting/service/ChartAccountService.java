package com.ut.tekir.accounting.service;

import com.ut.tekir.accounting.dto.ChartAccountDTO;
import com.ut.tekir.accounting.dto.ChartAccountSaveRequest;
import com.ut.tekir.accounting.dto.ChartAccountTreeDTO;
import com.ut.tekir.accounting.entity.AccountPlan;
import com.ut.tekir.accounting.entity.ChartAccount;
import com.ut.tekir.accounting.mapper.ChartAccountMapper;
import com.ut.tekir.accounting.repository.AccountPlanRepository;
import com.ut.tekir.accounting.repository.ChartAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChartAccountService {

    private final ChartAccountRepository chartAccountRepository;
    private final AccountPlanRepository accountPlanRepository;
    private final ChartAccountMapper mapper;

    public List<ChartAccountTreeDTO> getTree(Long planId) {
        List<ChartAccount> roots = chartAccountRepository.findByPlanIdAndParentIsNullOrderByCodeAsc(planId);
        return roots.stream().map(mapper::toTreeDto).toList();
    }

    public List<ChartAccountDTO> getFlat(Long planId) {
        return mapper.toDtoList(chartAccountRepository.findByPlanIdOrderByCodeAsc(planId));
    }

    public ChartAccountDTO getById(Long planId, Long id) {
        ChartAccount account = findAndValidate(planId, id);
        return mapper.toDto(account);
    }

    @Transactional
    public ChartAccountDTO create(Long planId, ChartAccountSaveRequest request) {
        AccountPlan plan = accountPlanRepository.findById(planId)
                .orElseThrow(() -> new NoSuchElementException("Hesap planı bulunamadı: " + planId));

        if (chartAccountRepository.existsByPlanIdAndCode(planId, request.code())) {
            throw new IllegalArgumentException("Bu hesap kodu bu planda zaten mevcut: " + request.code());
        }

        ChartAccount account = mapper.toEntity(request);
        account.setPlan(plan);
        account.setLevel(computeLevel(request.code()));

        if (request.parentId() != null) {
            ChartAccount parent = chartAccountRepository.findById(request.parentId())
                    .orElseThrow(() -> new NoSuchElementException("Üst hesap bulunamadı: " + request.parentId()));
            account.setParent(parent);
        }

        ChartAccount saved = chartAccountRepository.save(account);
        updatePlanAccountCount(plan);
        return mapper.toDto(saved);
    }

    @Transactional
    public ChartAccountDTO update(Long planId, Long id, ChartAccountSaveRequest request) {
        ChartAccount account = findAndValidate(planId, id);

        // If code changed, check uniqueness
        if (!account.getCode().equals(request.code())) {
            if (chartAccountRepository.existsByPlanIdAndCode(planId, request.code())) {
                throw new IllegalArgumentException("Bu hesap kodu bu planda zaten mevcut: " + request.code());
            }
        }

        mapper.updateEntity(request, account);
        account.setLevel(computeLevel(account.getCode()));

        if (request.parentId() != null) {
            ChartAccount parent = chartAccountRepository.findById(request.parentId())
                    .orElseThrow(() -> new NoSuchElementException("Üst hesap bulunamadı: " + request.parentId()));
            account.setParent(parent);
        } else {
            account.setParent(null);
        }

        return mapper.toDto(chartAccountRepository.save(account));
    }

    @Transactional
    public void delete(Long planId, Long id) {
        ChartAccount account = findAndValidate(planId, id);
        if (!account.getChildren().isEmpty()) {
            throw new IllegalStateException("Alt hesapları olan bir hesap silinemez");
        }
        chartAccountRepository.delete(account);
        accountPlanRepository.findById(planId).ifPresent(this::updatePlanAccountCount);
    }

    public List<ChartAccountDTO> suggest(Long planId, String query) {
        return mapper.toDtoList(
                chartAccountRepository.suggestAccounts(planId, query, PageRequest.of(0, 20)).getContent()
        );
    }

    private ChartAccount findAndValidate(Long planId, Long id) {
        ChartAccount account = chartAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hesap bulunamadı: " + id));
        if (!account.getPlan().getId().equals(planId)) {
            throw new IllegalArgumentException("Hesap bu plana ait değil");
        }
        return account;
    }

    private int computeLevel(String code) {
        int len = code.length();
        if (len == 1) return 1;
        if (len == 2) return 2;
        if (len == 3) return 3;
        if (len <= 5) return 4;
        return 5;
    }

    private void updatePlanAccountCount(AccountPlan plan) {
        long count = chartAccountRepository.countByPlanId(plan.getId());
        plan.setAccountCount((int) count);
        accountPlanRepository.save(plan);
    }
}

package com.ut.tekir.billing.service;

import com.ut.tekir.billing.entity.Plan;
import com.ut.tekir.billing.repository.PlanRepository;
import com.ut.tekir.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional(readOnly = true)
    public Plan findById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan", id));
    }

    @Transactional(readOnly = true)
    public Plan findByCode(String code) {
        return planRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Plan", "code=" + code));
    }

    @Transactional(readOnly = true)
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Transactional
    public Plan create(Plan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    public Plan update(Long id, Plan updates) {
        Plan plan = findById(id);
        plan.setName(updates.getName());
        plan.setMaxUsers(updates.getMaxUsers());
        plan.setMaxInvoicesPerMonth(updates.getMaxInvoicesPerMonth());
        plan.setMaxProducts(updates.getMaxProducts());
        plan.setMaxContacts(updates.getMaxContacts());
        plan.setMaxStorageMb(updates.getMaxStorageMb());
        plan.setMonthlyPrice(updates.getMonthlyPrice());
        plan.setYearlyPrice(updates.getYearlyPrice());
        plan.setFeatures(updates.getFeatures());
        plan.setTrialDays(updates.getTrialDays());
        plan.setActive(updates.getActive());
        return planRepository.save(plan);
    }
}

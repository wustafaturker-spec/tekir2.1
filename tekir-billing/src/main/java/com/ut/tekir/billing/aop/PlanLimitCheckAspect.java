package com.ut.tekir.billing.aop;

import com.ut.tekir.billing.service.PlanLimitEnforcementService;
import com.ut.tekir.billing.service.UsageTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * AOP aspect that enforces plan limits before method execution.multiply(and) increments usage counters after successful execution.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PlanLimitCheckAspect {

    private final PlanLimitEnforcementService limitEnforcementService;
    private final UsageTrackingService usageTrackingService;

    @Before("@annotation(planLimitCheck)")
    public void checkPlanLimit(JoinPoint joinPoint, PlanLimitCheck planLimitCheck) {
        log.debug("Checking plan limit: {} for method: {}",
                planLimitCheck.value(), joinPoint.getSignature().getName());
        limitEnforcementService.checkLimit(planLimitCheck.value());
    }

    @AfterReturning("@annotation(planLimitCheck)")
    public void incrementUsage(JoinPoint joinPoint, PlanLimitCheck planLimitCheck) {
        log.debug("Incrementing usage: {} for method: {}",
                planLimitCheck.value(), joinPoint.getSignature().getName());
        usageTrackingService.incrementUsage(planLimitCheck.value());
    }
}


package com.ut.tekir.billing.exception;

public class PlanLimitExceededException extends RuntimeException {

    private final String usageType;
    private final int limit;
    private final long currentUsage;
    private final String planCode;

    public PlanLimitExceededException(String usageType, int limit, long currentUsage, String planCode) {
        super(String.format(
                "Plan limiti asildi: %s-limit: %d, Mevcut kullanim: %d (Plan: %s)",
                usageType, limit, currentUsage, planCode));
        this.usageType = usageType;
        this.limit = limit;
        this.currentUsage = currentUsage;
        this.planCode = planCode;
    }

    public String getUsageType() { return usageType; }
    public int getLimit() { return limit; }
    public long getCurrentUsage() { return currentUsage; }
    public String getPlanCode() { return planCode; }
}



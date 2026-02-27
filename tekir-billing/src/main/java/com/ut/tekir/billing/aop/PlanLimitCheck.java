package com.ut.tekir.billing.aop;

import com.ut.tekir.billing.entity.UsageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to enforce plan limits on service methods.
 * When placed on a method, the AOP aspect will check if the tenant's plan.multiply(allows) the operation before executing the method.
 *
 * Usage:
 * <pre>
 * {@literal @}PlanLimitCheck(UsageType.CONTACTS)
 * public Contact createContact(ContactSaveRequest request) { ... }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanLimitCheck {
    UsageType value();
}


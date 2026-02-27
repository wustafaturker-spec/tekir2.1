package com.ut.tekir.api.controller;

import com.ut.tekir.billing.exception.PlanLimitExceededException;
import com.ut.tekir.billing.exception.SubscriptionExpiredException;
import com.ut.tekir.common.dto.ErrorResponse;
import com.ut.tekir.common.dto.ValidationErrorResponse;
import com.ut.tekir.common.exception.BusinessException;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.report.ReportGenerationException;
import com.ut.tekir.tenant.exception.TenantAccessDeniedException;
import com.ut.tekir.tenant.exception.TenantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        log.warn("Business error: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTenantNotFound(TenantNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("TENANT_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(TenantAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleTenantAccessDenied(TenantAccessDeniedException ex) {
        log.warn("Tenant access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse("TENANT_ACCESS_DENIED", ex.getMessage()));
    }

    @ExceptionHandler(PlanLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handlePlanLimit(PlanLimitExceededException ex) {
        log.warn("Plan limit exceeded: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
            .body(new ErrorResponse("PLAN_LIMIT_EXCEEDED", ex.getMessage()));
    }

    @ExceptionHandler(SubscriptionExpiredException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionExpired(SubscriptionExpiredException ex) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
            .body(new ErrorResponse("SUBSCRIPTION_EXPIRED", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse.FieldError> errors = ex.getBindingResult()
            .getFieldErrors().stream()
            .map(fe -> new ValidationErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage()))
            .toList();
        return ResponseEntity.badRequest()
            .body(new ValidationErrorResponse(errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse("FORBIDDEN", "Yetkiniz bulunmamaktadir"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse("UNAUTHORIZED", "Gecersiz kullanici adi veya sifre"));
    }

    @ExceptionHandler(ReportGenerationException.class)
    public ResponseEntity<ErrorResponse> handleReport(ReportGenerationException ex) {
        log.error("Report generation failed", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("REPORT_ERROR", "Rapor olusturulamadi"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("INTERNAL_ERROR", "Beklenmeyen bir hata olustu"));
    }
}

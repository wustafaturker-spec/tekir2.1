package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base class for auditable entities.
 * Maps to existing CREATE_DATE, UPDATE_DATE, CREATE_USER, UPDATE_USER columns.
 * Includes @TenantId for Hibernate 6 multi-tenancy (automatic WHERE TENANT_ID =
 * ? filtering).
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@jakarta.persistence.TableGenerator(name = "genericSeq", table = "TEKIR_SEQUENCE", pkColumnName = "NAME", valueColumnName = "NEXT_VALUE", pkColumnValue = "GENERIC_SEQ", allocationSize = 10)
public abstract class AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @CreatedDate
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @CreatedBy
    @Column(name = "CREATE_USER", length = 10, updatable = false)
    private String createUser;

    @LastModifiedBy
    @Column(name = "UPDATE_USER", length = 10)
    private String updateUser;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}

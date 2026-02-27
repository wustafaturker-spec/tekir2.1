package com.ut.tekir.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Yetkili kişi kaydı.
 * Bir Contact'a ait iletişim kurulabilecek kişileri temsil eder.
 */
@Entity
@Table(name = "CONTACT_PERSON_ENTRY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ContactPersonEntry extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    @NotNull
    private Contact contact;

    @Column(name = "FIRST_NAME", length = 100)
    @Size(max = 100)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    @Size(max = 100)
    private String lastName;

    @Column(name = "JOB_TITLE", length = 100)
    @Size(max = 100)
    private String jobTitle;

    @Column(name = "DEPARTMENT", length = 100)
    @Size(max = 100)
    private String department;

    @Column(name = "EMAIL", length = 255)
    @Size(max = 255)
    @Email
    private String email;

    @Column(name = "PHONE", length = 30)
    @Size(max = 30)
    private String phone;

    @Column(name = "MOBILE", length = 30)
    @Size(max = 30)
    private String mobile;

    @Column(name = "IS_DEFAULT")
    private Boolean isDefault = Boolean.FALSE;

    @Column(name = "NOTE", length = 500)
    @Size(max = 500)
    private String note;
}

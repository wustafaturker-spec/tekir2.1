package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Employee entity.
 */
@Entity
@Table(name = "EMPLOYEE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Employee extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERSONNEL_NO", nullable = false, unique = true, length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String personnelNo;

    @Column(name = "SSN", length = 11)
    @Size(max = 11)
    private String ssn;

    @Column(name = "PASSPORT_NO", length = 11)
    @Size(max = 11)
    private String passportNo;

    @Column(name = "SSK_NO", length = 20)
    @Size(max = 20)
    private String sskNo;

    @Column(name = "FIRST_NAME", length = 30)
    @Size(max = 30)
    private String firstName;

    @Column(name = "LAST_NAME", length = 20)
    @Size(max = 20)
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
}

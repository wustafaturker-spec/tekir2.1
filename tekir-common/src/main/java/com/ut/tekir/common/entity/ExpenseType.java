package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Expense type definition entity.
 */
@Entity
@Table(name = "EXPENSE_TYPE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ExpenseType extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, unique = true, nullable = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String code;

    @Column(name = "NAME", length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}

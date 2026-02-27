package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Cheque stub entity.
 */
@Entity
@Table(name = "CHEQUE_STUB")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ChequeStub extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHEQUE_ID")
    private Cheque cheque;

    @Column(name = "STUB_CODE", length = 20)
    @Size(max = 20)
    private String stubCode;

    @Column(name = "STUB_NO", length = 20)
    @Size(max = 20)
    private String stubNo;
}

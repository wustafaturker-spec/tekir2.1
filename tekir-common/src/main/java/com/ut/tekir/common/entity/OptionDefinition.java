package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "OPTION_DEFINITION") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class OptionDefinition extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @Column(name = "OPT_KEY", length = 100) @Size(max = 100) private String optKey;
    @Column(name = "OPT_VALUE", length = 500) @Size(max = 500) private String optValue;
}

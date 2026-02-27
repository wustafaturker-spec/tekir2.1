package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Sequence definition entity for auto-numbering.
 */
@Entity
@Table(name = "SEQUENCE")
public class Sequence extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 50, nullable = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Column(name = "PREFIX", length = 10)
    @Size(max = 10)
    private String prefix;

    @Column(name = "SUFFIX", length = 10)
    @Size(max = 10)
    private String suffix;

    @Column(name = "NEXT_VALUE")
    private Long nextValue = 1L;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    public String getSuffix() { return suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
    public Long getNextValue() { return nextValue; }
    public void setNextValue(Long nextValue) { this.nextValue = nextValue; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}

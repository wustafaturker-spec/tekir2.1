package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * City entity with province relationship.
 * Note: @Cascade(DELETE_ORPHAN) → orphanRemoval = true
 */
@Entity
@Table(name = "CITY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class City extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    @NotNull
    @Size(min = 1, max = 10)
    private String code;

    @Column(name = "PLATE", length = 4, nullable = false)
    @NotNull
    @Size(min = 1, max = 4)
    private String plate;

    @Column(name = "NAME", length = 40, nullable = false)
    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @Column(name = "COUNTRY", length = 40)
    @Size(max = 40)
    private String country;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "WEIGHT")
    private Integer weight;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Province> provinces = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Boolean getSystem() { return system; }
    public void setSystem(Boolean system) { this.system = system; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }


    @Transient
    public String getCaption() {
        return "[" + getCode() + "] " + getName();
    }
}

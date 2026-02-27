package com.ut.tekir.common.dto.admin;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleDTO implements Serializable {
    private Long id;
    private String name;
    private String info;
    private Set<String> permissions = new HashSet<>();
}

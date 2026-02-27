package com.ut.tekir.common.dto.admin;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO implements Serializable {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private Boolean active;
    private String password; // Only used for creation/update, not returned in GET usually
    private List<RoleDTO> roles = new ArrayList<>();
}

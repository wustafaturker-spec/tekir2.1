package com.ut.tekir.service;

import com.ut.tekir.common.dto.admin.RoleDTO;
import com.ut.tekir.common.entity.Permission;
import com.ut.tekir.common.entity.Role;
import com.ut.tekir.common.entity.RolePermission;
import com.ut.tekir.repository.PermissionRepository;
import com.ut.tekir.repository.RolePermissionRepository;
import com.ut.tekir.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + id));
        return toDTO(role);
    }

    @Transactional
    public RoleDTO createRole(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setInfo(dto.getInfo());
        role = roleRepository.save(role);

        updatePermissions(role, dto.getPermissions());

        return toDTO(role);
    }

    @Transactional
    public RoleDTO updateRole(Long id, RoleDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + id));

        role.setName(dto.getName());
        role.setInfo(dto.getInfo());
        role = roleRepository.save(role);

        updatePermissions(role, dto.getPermissions());

        return toDTO(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private void updatePermissions(Role role, Set<String> permissionCodes) {
        // Clear existing permissions
        role.getRolePermissions().clear();
        rolePermissionRepository.deleteByRole(role); // ensure DB cleanup if orphanRemoval doesn't catch all cases immediately or for cleaner logic

        if (permissionCodes != null && !permissionCodes.isEmpty()) {
            for (String code : permissionCodes) {
                Permission permission = permissionRepository.findByPermissionCode(code).orElse(null);
                if (permission == null) {
                    // Create permission if it implies a new one, or skip/error. 
                    // For now, let's skip or assume it exists. 
                    // Better approach: Permissions should be pre-seeded. 
                    // If creating on the fly is needed:
                    permission = new Permission();
                    permission.setPermissionCode(code);
                    permission.setModule(code.split(":")[0]);
                    permission = permissionRepository.save(permission);
                }

                RolePermission rp = new RolePermission();
                rp.setRole(role);
                rp.setPermission(permission);
                role.getRolePermissions().add(rp);
            }
        }
        roleRepository.save(role);
    }

    private RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setInfo(role.getInfo());
        
        Set<String> perms = role.getRolePermissions().stream()
                .map(rp -> rp.getPermission().getPermissionCode())
                .collect(Collectors.toSet());
        dto.setPermissions(perms);
        
        return dto;
    }
}

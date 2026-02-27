package com.ut.tekir.service;

import com.ut.tekir.common.dto.admin.RoleDTO;
import com.ut.tekir.common.dto.admin.UserDTO;
import com.ut.tekir.common.entity.Role;
import com.ut.tekir.common.entity.User;
import com.ut.tekir.common.entity.UserRole;
import com.ut.tekir.repository.RoleRepository;
import com.ut.tekir.repository.UserRepository;
import com.ut.tekir.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id));
        return toDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO dto) {
        if (userRepository.findByUserName(dto.getUserName()).isPresent()) {
            throw new RuntimeException("Bu kullanıcı adı zaten kullanılıyor.");
        }

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setActive(dto.getActive() != null ? dto.getActive() : true);
        
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword("{bcrypt}" + passwordEncoder.encode(dto.getPassword()));
        }

        user = userRepository.save(user);
        updateRoles(user, dto.getRoles()); // Update roles handled here

        return toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id));

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        if (dto.getActive() != null) {
            user.setActive(dto.getActive());
        }

        // Only update password if provided
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword("{bcrypt}" + passwordEncoder.encode(dto.getPassword()));
        }

        user = userRepository.save(user);
        updateRoles(user, dto.getRoles());

        return toDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void updateRoles(User user, List<RoleDTO> roleDTOs) {
        // Clear existing roles
        user.getUserRoles().clear();
        userRoleRepository.deleteByUser(user); // Ensure cleanup

        if (roleDTOs != null) {
            for (RoleDTO roleDTO : roleDTOs) {
                Role role = roleRepository.findById(roleDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleDTO.getId()));
                
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                user.getUserRoles().add(userRole);
            }
        }
        userRepository.save(user);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());
        // Password is NOT returned for security

        List<RoleDTO> roles = user.getUserRoles().stream()
                .map(ur -> {
                    RoleDTO r = new RoleDTO();
                    r.setId(ur.getRole().getId());
                    r.setName(ur.getRole().getName());
                    return r;
                })
                .collect(Collectors.toList());
        dto.setRoles(roles);

        return dto;
    }
}

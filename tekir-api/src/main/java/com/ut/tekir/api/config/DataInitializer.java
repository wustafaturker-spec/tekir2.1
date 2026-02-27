package com.ut.tekir.api.config;

import com.ut.tekir.common.entity.Role;
import com.ut.tekir.common.entity.User;
import com.ut.tekir.common.entity.UserRole;
import com.ut.tekir.repository.RoleRepository;
import com.ut.tekir.repository.UserRepository;
import com.ut.tekir.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");

        createUserIfNotFound("admin", "admin", "admin@tekir.com", "Administrator");
        createUserIfNotFound("user", "user", "user@tekir.com", "Standard User");
    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            role.setInfo("Default " + roleName + " role");
            roleRepository.save(role);
            log.info("Role created: {}", roleName);
        }
    }

    private void createUserIfNotFound(String username, String rawPassword, String email, String fullName) {
        if (userRepository.findByUserName(username).isEmpty()) {
            User user = new User();
            user.setUserName(username);
            user.setPassword("{bcrypt}" + passwordEncoder.encode(rawPassword)); // Spring Security 5+ format
            user.setEmail(email);
            user.setFullName(fullName);
            user.setActive(true);

            // Assign ADMIN role to admin user, USER to others
            String roleName = username.equals("admin") ? "ADMIN" : "USER";
            Optional<Role> role = roleRepository.findByName(roleName);
            
            if (role.isPresent()) {
                userRepository.save(user); // Save user first to get ID
                
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role.get());
                userRoleRepository.save(userRole);
                
                log.info("User created and role assigned: {} -> {}", username, roleName);
            } else {
                userRepository.save(user);
                log.info("User created (no role found): {}", username);
            }
        }
    }
}

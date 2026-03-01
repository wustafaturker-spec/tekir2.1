package com.ut.tekir.api.config;

import com.ut.tekir.common.entity.*;
import com.ut.tekir.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // All permission codes used across controllers
    private static final List<String[]> ALL_PERMISSIONS = Arrays.asList(
        new String[]{"invoice:read",      "Read invoices",          "invoice"},
        new String[]{"invoice:create",    "Create invoices",        "invoice"},
        new String[]{"invoice:update",    "Update invoices",        "invoice"},
        new String[]{"invoice:delete",    "Delete invoices",        "invoice"},
        new String[]{"invoice:write",     "Write invoices",         "invoice"},
        new String[]{"contact:read",      "Read contacts",          "contact"},
        new String[]{"contact:create",    "Create contacts",        "contact"},
        new String[]{"contact:update",    "Update contacts",        "contact"},
        new String[]{"contact:delete",    "Delete contacts",        "contact"},
        new String[]{"product:read",      "Read products",          "product"},
        new String[]{"product:create",    "Create products",        "product"},
        new String[]{"product:update",    "Update products",        "product"},
        new String[]{"product:delete",    "Delete products",        "product"},
        new String[]{"order:read",        "Read orders",            "order"},
        new String[]{"order:create",      "Create orders",          "order"},
        new String[]{"order:update",      "Update orders",          "order"},
        new String[]{"order:delete",      "Delete orders",          "order"},
        new String[]{"payment:read",      "Read payments",          "payment"},
        new String[]{"payment:create",    "Create payments",        "payment"},
        new String[]{"payment:update",    "Update payments",        "payment"},
        new String[]{"payment:delete",    "Delete payments",        "payment"},
        new String[]{"bank:read",         "Read banks",             "bank"},
        new String[]{"bank:create",       "Create banks",           "bank"},
        new String[]{"bank:update",       "Update banks",           "bank"},
        new String[]{"bank:delete",       "Delete banks",           "bank"},
        new String[]{"stock:read",        "Read stock",             "stock"},
        new String[]{"stock:create",      "Create stock",           "stock"},
        new String[]{"stock:update",      "Update stock",           "stock"},
        new String[]{"stock:delete",      "Delete stock",           "stock"},
        new String[]{"account:read",      "Read accounts",          "account"},
        new String[]{"account:create",    "Create accounts",        "account"},
        new String[]{"account:update",    "Update accounts",        "account"},
        new String[]{"account:delete",    "Delete accounts",        "account"},
        new String[]{"finance:read",      "Read finance",           "finance"},
        new String[]{"finance:create",    "Create finance",         "finance"},
        new String[]{"finance:update",    "Update finance",         "finance"},
        new String[]{"finance:delete",    "Delete finance",         "finance"},
        new String[]{"employee:read",     "Read employees",         "employee"},
        new String[]{"employee:create",   "Create employees",       "employee"},
        new String[]{"employee:update",   "Update employees",       "employee"},
        new String[]{"employee:delete",   "Delete employees",       "employee"},
        new String[]{"expense:read",      "Read expenses",          "expense"},
        new String[]{"expense:create",    "Create expenses",        "expense"},
        new String[]{"expense:update",    "Update expenses",        "expense"},
        new String[]{"expense:delete",    "Delete expenses",        "expense"},
        new String[]{"cheque:read",       "Read cheques",           "cheque"},
        new String[]{"cheque:create",     "Create cheques",         "cheque"},
        new String[]{"cheque:update",     "Update cheques",         "cheque"},
        new String[]{"cheque:delete",     "Delete cheques",         "cheque"},
        new String[]{"department:read",   "Read departments",       "department"},
        new String[]{"department:create", "Create departments",     "department"},
        new String[]{"department:update", "Update departments",     "department"},
        new String[]{"department:delete", "Delete departments",     "department"},
        new String[]{"location:read",     "Read locations",         "location"},
        new String[]{"location:create",   "Create locations",       "location"},
        new String[]{"location:update",   "Update locations",       "location"},
        new String[]{"location:delete",   "Delete locations",       "location"},
        new String[]{"currency:read",     "Read currencies",        "currency"},
        new String[]{"currency:create",   "Create currencies",      "currency"},
        new String[]{"currency:delete",   "Delete currencies",      "currency"},
        new String[]{"promissory:read",   "Read promissory notes",  "promissory"},
        new String[]{"promissory:create", "Create promissory notes","promissory"},
        new String[]{"promissory:update", "Update promissory notes","promissory"},
        new String[]{"promissory:delete", "Delete promissory notes","promissory"},
        new String[]{"option:read",       "Read options",           "option"},
        new String[]{"option:update",     "Update options",         "option"},
        new String[]{"trade:create",      "Create trades",          "trade"},
        new String[]{"trade:update",      "Update trades",          "trade"},
        new String[]{"trade:delete",      "Delete trades",          "trade"},
        new String[]{"admin:read",          "Admin read",             "admin"},
        new String[]{"admin:write",         "Admin write",            "admin"},
        new String[]{"admin:maintenance",   "Admin maintenance",      "admin"},
        // Accounting module (Muhasebe)
        new String[]{"accounting:read",     "Read accounting",        "accounting"},
        new String[]{"accounting:create",   "Create accounting",      "accounting"},
        new String[]{"accounting:update",   "Update accounting",      "accounting"},
        new String[]{"accounting:delete",   "Delete accounting",      "accounting"},
        new String[]{"accounting:import",   "Import accounting data", "accounting"}
    );

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");

        seedPermissionsForAdminRole(adminRole);

        createUserIfNotFound("admin@tekir.com", "admin", "admin@tekir.com", "Administrator");
        createUserIfNotFound("user@tekir.com", "user", "user@tekir.com", "Standard User");

        // Ensure existing admin user has ADMIN role (fixes previous sessions)
        ensureAdminRoleAssigned("admin@tekir.com", adminRole);
    }

    private Role createRoleIfNotFound(String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            role.setInfo("Default " + roleName + " role");
            log.info("Role created: {}", roleName);
            return roleRepository.save(role);
        });
    }

    private void seedPermissionsForAdminRole(Role adminRole) {
        // Load existing mappings once to avoid repeated DB hits
        Set<String> existingMappedCodes = rolePermissionRepository.findAll().stream()
            .filter(rp -> rp.getRole().getId().equals(adminRole.getId()))
            .map(rp -> rp.getPermission().getPermissionCode())
            .collect(Collectors.toSet());

        for (String[] perm : ALL_PERMISSIONS) {
            String code = perm[0];
            String desc = perm[1];
            String module = perm[2];

            // Create permission if missing
            Permission permission = permissionRepository.findByPermissionCode(code).orElseGet(() -> {
                Permission p = new Permission();
                p.setPermissionCode(code);
                p.setDescription(desc);
                p.setModule(module);
                log.info("Permission created: {}", code);
                return permissionRepository.save(p);
            });

            // Assign to ADMIN role if not already mapped
            if (!existingMappedCodes.contains(code)) {
                RolePermission rp = new RolePermission();
                rp.setRole(adminRole);
                rp.setPermission(permission);
                rolePermissionRepository.save(rp);
                log.info("Permission {} assigned to ADMIN role", code);
            }
        }
    }

    private void createUserIfNotFound(String username, String rawPassword, String email, String fullName) {
        if (userRepository.findByUserName(username).isEmpty()) {
            User user = new User();
            user.setUserName(username);
            user.setPassword("{bcrypt}" + passwordEncoder.encode(rawPassword));
            user.setEmail(email);
            user.setFullName(fullName);
            user.setActive(true);

            // Fixed: was username.equals("admin") — admin username is admin@tekir.com
            String roleName = username.equals("admin@tekir.com") ? "ADMIN" : "USER";
            Optional<Role> role = roleRepository.findByName(roleName);

            if (role.isPresent()) {
                userRepository.save(user);

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

    /**
     * Ensures the admin user has the ADMIN role even if they were created
     * in a previous session before the role assignment bug was fixed.
     */
    private void ensureAdminRoleAssigned(String adminUsername, Role adminRole) {
        userRepository.findByUserName(adminUsername).ifPresent(adminUser -> {
            List<UserRole> existingRoles = userRoleRepository.findAll().stream()
                .filter(ur -> ur.getUser().getId().equals(adminUser.getId()))
                .collect(Collectors.toList());

            boolean hasAdminRole = existingRoles.stream()
                .anyMatch(ur -> "ADMIN".equals(ur.getRole().getName()));

            if (!hasAdminRole) {
                // Remove wrong roles (USER) and assign ADMIN
                existingRoles.forEach(userRoleRepository::delete);

                UserRole userRole = new UserRole();
                userRole.setUser(adminUser);
                userRole.setRole(adminRole);
                userRoleRepository.save(userRole);
                log.info("ADMIN role re-assigned to existing user: {}", adminUsername);
            }
        });
    }
}

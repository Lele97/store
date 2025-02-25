package com.webapp.enterprice.spring.auth.service.service;

import com.webapp.enterprice.spring.auth.service.entity.Role;
import com.webapp.enterprice.spring.auth.service.entity.User;
import com.webapp.enterprice.spring.auth.service.entity.UserRequest;
import com.webapp.enterprice.spring.auth.service.repository.RoleRepository;
import com.webapp.enterprice.spring.auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * Service for registration new User
     *
     * @param userRequest
     * @throws Exception
     */
    public void registration(UserRequest userRequest) throws Exception {
        if (repository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new Exception(userRequest.getUsername() + " already exists");
        }
        try {
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());

            Set<Role> roles = new HashSet<>();
            for (Map<String, Object> roleMap : userRequest.getRoles()) {
                Long roleId = ((Number) roleMap.get("id")).longValue();
                String roleName = (String) roleMap.get("name");

                Optional<Role> existingRole = roleRepository.findById(roleId);
                if (existingRole.isPresent()) {
                    roles.add(existingRole.get());
                } else {
                    Role newRole = new Role();
                    newRole.setId(roleId);
                    newRole.setName(roleName);
                    roleRepository.save(newRole);
                    roles.add(newRole);
                }
            }
            user.setRoles(roles);

            repository.save(user);
        } catch (Exception e) {
            throw new Exception("Registration failed: " + e.getMessage(), e);
        }
    }
}

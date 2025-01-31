package com.webapp.enterprice.spring.auth.service.repository;

import com.webapp.enterprice.spring.auth.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

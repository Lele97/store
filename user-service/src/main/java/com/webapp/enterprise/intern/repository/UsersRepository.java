package com.webapp.enterprise.intern.repository;

import com.webapp.enterprise.intern.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmail(String email);
    Users findUsersByUsername(String username);
}

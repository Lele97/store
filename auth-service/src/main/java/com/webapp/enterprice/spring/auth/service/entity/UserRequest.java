package com.webapp.enterprice.spring.auth.service.entity;

import lombok.*;
import java.util.Set;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private Set<Map<String, Object>> roles;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Map<String, Object>> getRoles() {
        return roles;
    }

    public void setRoles(Set<Map<String, Object>> roles) {
        this.roles = roles;
    }
}

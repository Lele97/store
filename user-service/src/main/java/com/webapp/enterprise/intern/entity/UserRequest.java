package com.webapp.enterprise.intern.entity;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String role;
}

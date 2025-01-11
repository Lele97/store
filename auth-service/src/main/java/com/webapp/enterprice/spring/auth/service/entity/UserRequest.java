package com.webapp.enterprice.spring.auth.service.entity;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

        private String username;

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

        public String getRoles() {
                return roles;
        }

        public void setRoles(String roles) {
                this.roles = roles;
        }

        private String password;
        private String email;
        private String roles;
}

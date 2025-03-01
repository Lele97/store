package com.webapp.enterprice.spring.auth.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db")
public class HasiCorpVaultSecretData {

    private String password;
    private String username;

    public HasiCorpVaultSecretData() {
    }

    @Autowired
    public HasiCorpVaultSecretData(@Value("${db.password}") String password, @Value("${db.username}") String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package com.webapp.enterprice.spring.auth.service;

import com.webapp.enterprice.spring.auth.service.config.HasiCorpVaultSecretData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(value = HasiCorpVaultSecretData.class)
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}

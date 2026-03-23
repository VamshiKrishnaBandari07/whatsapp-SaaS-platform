package com.whatsapp.saas.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.whatsapp.saas")
@EnableJpaRepositories(basePackages = "com.whatsapp.saas")
@EntityScan(basePackages = "com.whatsapp.saas")
@EnableScheduling
public class SaaSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaaSApplication.class, args);
    }
}

package com.ut.tekir.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.ut.tekir")
@EntityScan(basePackages = "com.ut.tekir")
@EnableJpaRepositories(basePackages = {
    "com.ut.tekir.repository",
    "com.ut.tekir.tenant.repository",
    "com.ut.tekir.billing.repository"
})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
public class TekirApplication {

    public static void main(String[] args) {
        SpringApplication.run(TekirApplication.class, args);
    }
}

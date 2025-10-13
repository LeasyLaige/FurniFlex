package com.suarez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.suarez.entity")
@EnableJpaRepositories(basePackages = "com.suarez.repository")
public class FurniFlexMSApplication {
    public static void main(String[] args) { SpringApplication.run(FurniFlexMSApplication.class, args); }
}

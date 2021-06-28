package com.briden.boot.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableJpaRepositories("com.briden.boot.repository.jpa")
@EntityScan("com.briden.boot.entity.jpa")
@EnableScheduling
public class AppConfig {
}

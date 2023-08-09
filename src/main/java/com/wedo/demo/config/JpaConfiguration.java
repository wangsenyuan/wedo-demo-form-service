package com.wedo.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.wedo.demo.domain.message.repository")
public class JpaConfiguration {
}

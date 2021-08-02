package com.example.carworkshops.config;

import com.example.carworkshops.repository.base.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.carworkshops.repository",
        repositoryBaseClass = BaseRepositoryImpl.class)
public class JpaConfig {
}

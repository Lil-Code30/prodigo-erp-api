package com.licode.prodigoerp.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PathConfig {

    @Bean(name = "publicPaths")
    public List<String> publicPaths() {
        return List.of(
                "/api/auth/register",
                "/api/auth/login",
                "/api/auth/refresh"
        );
    }

    @Bean("securePaths")
    public List<String> securePaths() {
        return List.of("/api/**");
    }
}

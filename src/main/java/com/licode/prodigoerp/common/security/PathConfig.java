package com.licode.prodigoerp.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PathConfig {

    @Bean(name = "publicPaths")
    public List<String> publicPaths() {
        return List.of(
                "/api/public",
                "/api/auth/*"
        );
    }

    @Bean("securePaths")
    public List<String> securePaths() {
        return List.of("/api/**");
    }
}

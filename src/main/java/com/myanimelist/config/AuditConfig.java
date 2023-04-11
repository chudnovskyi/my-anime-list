package com.myanimelist.config;

import com.myanimelist.MyAnimeListApplication;
import com.myanimelist.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = MyAnimeListApplication.class)
@RequiredArgsConstructor
@Configuration
public class AuditConfig {

    private final AuthenticationFacade authenticationFacade;

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(authenticationFacade.getUsername());
    }
}

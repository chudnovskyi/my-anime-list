package com.myanimelist.config;

import com.myanimelist.security.AuthenticationSuccessHandlerImpl;
import com.myanimelist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class SecurityConfig {

    private final UserService userService;
    private final AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userService)

                .authorizeRequests()
                    .antMatchers("/login*/**", "/register*/**", "/swagger*/**").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/**").hasRole("USER")
                .and()

                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .successHandler(authenticationSuccessHandlerImpl)
                    .permitAll()
                .and()

                .logout()
                    .permitAll()
                .and()

                .exceptionHandling()
                    .accessDeniedPage("/access-denied");

        return http.build();
    }
}

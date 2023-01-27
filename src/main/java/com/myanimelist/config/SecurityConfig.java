package com.myanimelist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.myanimelist.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Lazy
	@Autowired
	private UserService userService;

	@Lazy
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

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
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.userDetailsService(userService)
			
			.authorizeRequests()
				.antMatchers("/home").hasRole("USER")
				.antMatchers("/anime/**").hasRole("USER")
				.antMatchers("/list/**").hasRole("USER")
				.antMatchers("/reviews/**").hasRole("USER")
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/activate/*").permitAll()
			.and()
			
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/authenticate")
				.successHandler(customAuthenticationSuccessHandler)
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

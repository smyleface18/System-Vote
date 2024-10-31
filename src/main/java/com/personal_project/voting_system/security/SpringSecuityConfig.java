package com.personal_project.voting_system.security;

import com.personal_project.voting_system.security.filter.JwtAuthenticationFilter;
import com.personal_project.voting_system.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecuityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SpringSecuityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    AuthenticationManager authenticationManager () throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/api/create/user","/api/voted/{id_vote}/option/{id_option}").permitAll().anyRequest()
                        .authenticated())
                        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                        .addFilter(new JwtValidationFilter(authenticationManager()))
                        .csrf(config -> config.disable())
                        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
    }


}

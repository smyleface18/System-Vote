package com.personal_project.voting_system.security;

import com.personal_project.voting_system.respository.IRepositoryRole;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.RepositoryRoleImpl;
import com.personal_project.voting_system.respository.RepositoryUserImpl;
import com.personal_project.voting_system.security.filter.JwtAuthenticationFilter;
import com.personal_project.voting_system.security.filter.JwtValidationFilter;
import com.personal_project.voting_system.services.ServiceUser;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.EntityManagerHolder;
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
    private final EntityManager entityManager;

    @Autowired
    public SpringSecuityConfig(AuthenticationConfiguration authenticationConfiguration, EntityManager entityManager) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.entityManager = entityManager;
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
                        .requestMatchers("/user/create","/api/**","/api/validation/email/**","user/recheck").permitAll()
                        .anyRequest()
                        .authenticated())
                        .addFilter(new JwtAuthenticationFilter(authenticationManager(),entityManager))
                        .addFilter(new JwtValidationFilter(authenticationManager()))
                        .csrf(config -> config.disable())
                        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
    }


}

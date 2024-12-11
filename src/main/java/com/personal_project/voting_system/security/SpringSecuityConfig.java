package com.personal_project.voting_system.security;

import com.personal_project.voting_system.respository.IRepositoryRole;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.RepositoryRoleImpl;
import com.personal_project.voting_system.respository.RepositoryUserImpl;
import com.personal_project.voting_system.security.filter.JwtAuthenticationFilter;
import com.personal_project.voting_system.security.filter.JwtValidationFilter;
import jakarta.persistence.EntityManager;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecuityConfig  {

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
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/user/create","/api/**","/api/validation/email/**","/user/recheck","/img/**").permitAll()
                        .anyRequest()
                        .authenticated())
                        .addFilter(new JwtAuthenticationFilter(authenticationManager(),entityManager))
                        .addFilter(new JwtValidationFilter(authenticationManager()))
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
                        .logout(LogoutConfigurer::permitAll)
                        .build();
    }
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}

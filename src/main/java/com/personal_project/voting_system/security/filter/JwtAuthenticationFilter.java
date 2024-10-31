package com.personal_project.voting_system.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_project.voting_system.dtos.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.*;

import static com.personal_project.voting_system.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;



    @Autowired
    public JwtAuthenticationFilter( AuthenticationManager authenticationManager1) {
        this.authenticationManager = authenticationManager1;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User user = null;
        String name = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            name = user.getName();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(name,password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String name = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Map<String,Object> claims = new LinkedHashMap<>();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        String token = Jwts.builder()
                .subject(name)
                .claims(claims)
                .expiration(new Date( System.currentTimeMillis() + 10800000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION,PREFIX_TOKEN.concat(token));

        Map<String, String> body = new LinkedHashMap<>();
        body.put("user", user.getUsername());
        body.put("token",token);
        body.put("message", String.format("Hola %s has iniciado sesion con exito!",name));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message","Error en la autenticacion, nombre de usuario o contraseña incorrecta!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(CONTENT_TYPE);
    }
}

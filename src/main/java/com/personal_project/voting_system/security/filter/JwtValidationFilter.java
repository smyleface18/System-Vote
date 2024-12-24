package com.personal_project.voting_system.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_project.voting_system.security.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import java.io.IOException;
import java.util.*;

import static com.personal_project.voting_system.security.TokenJwtConfig.*;

@Slf4j
public class JwtValidationFilter extends BasicAuthenticationFilter {

    private static final List<String> PUBLIC_ROUTES = Arrays.asList(
            "/user/create", "/api/", "/user/validation/email/", "/user/recheck"
    );

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if(isPublicRoute(request.getServletPath())){
            chain.doFilter(request,response);
            return;
        }

        if( header == null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request,response);
            return;
        }
        String token = header.replace(PREFIX_TOKEN,"");
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String name = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class,
                                    SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(name,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        catch(JwtException e){
            Map<String,String> body = new LinkedHashMap<>();
            body.put("error",e.getMessage());
            body.put("message","El token JWT es invalido!");

            response.getWriter().write( new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
        chain.doFilter(request,response);
    }

    private boolean isPublicRoute(String servletPath) {
        return PUBLIC_ROUTES.stream().anyMatch(route -> route.startsWith(servletPath));
    }
}

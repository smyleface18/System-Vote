package com.personal_project.voting_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;


import java.util.Map;

import static com.personal_project.voting_system.security.TokenJwtConfig.SECRET_KEY;

@Component
public class TokenData {

    public Map<String,Object> Readclaims(String token){
        Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
        return (Map<String, Object>) claims;
    }

}

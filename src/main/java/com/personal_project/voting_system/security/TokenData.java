package com.personal_project.voting_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Map;

import static com.personal_project.voting_system.security.TokenJwtConfig.SECRET_KEY;

@Component
public class TokenData {

    public Map<String,Object> Readclaims(String token){
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    public String generateTokenEmail(Map <String,String> body){
        return Jwts.builder().subject("email")
                .claims(body)
                .expiration(new Date(System.currentTimeMillis()+3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();
    }

}

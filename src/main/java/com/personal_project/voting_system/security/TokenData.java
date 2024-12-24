package com.personal_project.voting_system.security;


import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Map;

import static com.personal_project.voting_system.security.TokenJwtConfig.SECRET_KEY;

@Component
@Log4j2
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

    public String generateTokenVote(Map <String,Object> body){
        return Jwts.builder().subject("vote")
                .claim("idVote",body.get("idVote"))
                .claim("dateInitial",new Date(System.currentTimeMillis()+Integer.parseInt(String.valueOf(body.get("dateInitial")))))
                .claim("dateEnd",new Date(System.currentTimeMillis()+Integer.parseInt(String.valueOf(body.get("dateEnd")))))
                .expiration(new Date(System.currentTimeMillis()+Integer.parseInt(String.valueOf(body.get("dateEnd"))))) //por alguna razon le reduce por 1000 a los milisegundos por eso decidi guardar las fechas en los claims
                .issuedAt(new Date(System.currentTimeMillis()+Integer.parseInt(String.valueOf(body.get("dateInitial")))))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Map<String,Object> verifyDate(String token){
         return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

}

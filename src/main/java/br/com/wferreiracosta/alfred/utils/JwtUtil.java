package br.com.wferreiracosta.alfred.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username){
        final var expirationTime = new Date(System.currentTimeMillis()+expiration);
        return Jwts.builder()
                .setSubject(username).setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

}

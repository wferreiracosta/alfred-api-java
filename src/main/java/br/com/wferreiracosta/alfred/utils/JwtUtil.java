package br.com.wferreiracosta.alfred.utils;

import br.com.wferreiracosta.alfred.exception.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.Objects.isNull;

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

    public String getUsername(String token) {
        final var claims = getClaims(token);
        if(!isNull(claims)) {
            return claims.getSubject();
        }
        return null;
    }

    public boolean tokenValido(String token) {
        final var claims = getClaims(token);
        if (!isNull(claims)) {
            final var username = claims.getSubject();
            final var expirationDate = claims.getExpiration();
            final var now = new Date(System.currentTimeMillis());
            if (!isNull(username) && !isNull(expirationDate) && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

}

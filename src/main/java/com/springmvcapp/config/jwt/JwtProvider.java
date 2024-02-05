package com.springmvcapp.config.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtProvider {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String login) {
        var expirationDate = Date.from(LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.UTC));

        return Jwts.builder()
                .subject(login)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String getLoginFromToken(String token) {
        var payload = Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token).getPayload();
        return payload.getSubject();
    }
}

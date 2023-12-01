package com.socialsync.usersmicroservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(String id) {
        String token=  Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }

    public String getIdFromToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw  new Exception("Exceptie la validatea token-ului");
        }
    }
}

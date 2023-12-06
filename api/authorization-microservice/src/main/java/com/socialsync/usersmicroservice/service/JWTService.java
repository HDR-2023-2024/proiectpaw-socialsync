package com.socialsync.usersmicroservice.service;

import com.socialsync.usersmicroservice.pojo.AuthorizedInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(String id, String role) {
        String token = Jwts.builder()
                .setId(id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }


    public AuthorizedInfo decodeToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getId();
            String role = (String) claims.get("role");

            return new AuthorizedInfo(userId, role);
        } catch (SignatureException e) {
            throw new Exception("Token invalid sau semnatura invalida");
        } catch (Exception e) {
            throw new Exception("Exceptie la validarea token-ului");
        }
    }
}
package com.socialsync.usersmicroservice.service;

import com.socialsync.usersmicroservice.pojo.AuthorizedInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class JWTService {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    private static String SECRET_KEY;

    private static String SecretKeySingleton() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGenerator.generateKey();
        // cheia secreta
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String getInstance() throws NoSuchAlgorithmException {
        if (SECRET_KEY == null) {
            synchronized (String.class) {
               SECRET_KEY = SecretKeySingleton();
            }
        }
        return SECRET_KEY;
    }


    public String generateAccessToken(String id, String role) throws NoSuchAlgorithmException {
        String token = Jwts.builder()
                .setId(id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + getInstance()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }


    public AuthorizedInfo decodeToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getInstance())
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
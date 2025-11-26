package com.example.tp_grupo6.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenUtil {

    // puedes mover esto a application.properties si quieres
    // tp.jwt.secret=mi_clave_super_secreta_1234567890
    @Value("${tp.jwt.secret:mi_clave_super_secreta_1234567890}")
    private String secret;

    // 1 hora
    @Value("${tp.jwt.expiration-ms:3600000}")
    private long expirationMs;

    private SecretKey key;             // <--- SecretKey, no java.security.Key

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);   // devuelve SecretKey
    }

    // --------- generar token ---------
    public String generateToken(String username) {
        Date ahora = new Date();
        Date vencimiento = new Date(ahora.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(ahora)
                .expiration(vencimiento)
                .signWith(key)
                .compact();
    }

    // --------- validar token ---------
    public boolean validateToken(String token) {
        try {
            // si parsea sin lanzar excepción, es válido
            Jwts.parser()
                    .verifyWith(key)            // ahora sí compila
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
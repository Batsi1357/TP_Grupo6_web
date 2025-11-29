package com.example.tp_grupo6.security;

import io.jsonwebtoken.*;
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
        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("🔑 JwtTokenUtil - Secret cargado: " + secret);
        System.out.println("🔑 JwtTokenUtil - Key bytes length: " + keyBytes.length);
    }

    public String generateToken(String username) {
        Date ahora = new Date();
        Date vencimiento = new Date(ahora.getTime() + expirationMs);
        System.out.println("🔑 Generando token con secret: " + secret);

        return Jwts.builder()
                .subject(username)
                .issuedAt(ahora)
                .expiration(vencimiento)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("🔑 [VALIDATE] Validando token...");
            System.out.println("🔑 [VALIDATE] Secret en uso: " + secret);
            System.out.println("🔑 [VALIDATE] Token recibido: " + token.substring(0, 50) + "...");

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            System.out.println("✅ [VALIDATE] Token válido!");
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ [VALIDATE] Token expirado: " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            System.out.println("❌ [VALIDATE] JWT no soportado: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("❌ [VALIDATE] JWT malformado: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            System.out.println("❌ [VALIDATE] Firma inválida: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ [VALIDATE] Argumento inválido: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("❌ [VALIDATE] Error desconocido: " + e.getMessage());
            e.printStackTrace();
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
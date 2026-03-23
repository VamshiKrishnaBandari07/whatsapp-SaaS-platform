package com.whatsapp.saas.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:defaultSecretKeyWithAtLeast32BytesLengthForHmacSha256AsFallback}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs:86400000}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication, UUID tenantId) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .claim("tenantId", tenantId == null ? "" : tenantId.toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public UUID getTenantIdFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        String tenantIdStr = claims.get("tenantId", String.class);
        if (tenantIdStr != null && !tenantIdStr.isBlank()) {
            return UUID.fromString(tenantIdStr);
        }
        return null;
    }
}

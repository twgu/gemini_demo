package com.twgu.demo.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public enum TokenStatus {
        VALID,
        EXPIRED,
        INVALID
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityInSeconds;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createAccessToken(String userName, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(userName, claims, accessTokenValidityInSeconds);
    }

    public String createRefreshToken(String userName) {
        return createToken(userName, new HashMap<>(), refreshTokenValidityInSeconds);
    }

    private String createToken(String subject, Map<String, Object> claims, long validityInSeconds) {
        final long now = Instant.now().toEpochMilli();
        final Date validity = new Date(now + (validityInSeconds * 1000));

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public TokenStatus validateToken(String token) {
        try {
            extractAllClaims(token);
            return TokenStatus.VALID;
        } catch (ExpiredJwtException ex) {
            return TokenStatus.EXPIRED;
        } catch (Exception ex) {
            return TokenStatus.INVALID;
        }
    }

    public String extractUserName(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException ex) {
            return ex.getClaims().getSubject();
        }
    }

    public String extractRole(String token) {
        try {
            return extractClaim(token, claims -> claims.get("role", String.class));
        } catch (ExpiredJwtException ex) {
            return ex.getClaims().get("role", String.class);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

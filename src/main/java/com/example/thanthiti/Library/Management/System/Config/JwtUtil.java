package com.example.thanthiti.Library.Management.System.Config;

import com.example.thanthiti.Library.Management.System.Entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecretB64;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationInMs;

    @Value("${jwt.issuer:library-management-system}")
    private String issuer;

    @Value("${jwt.audience:library-users}")
    private String audience;

    @Value("${jwt.clockSkewSeconds:60}")
    private long clockSkewSeconds;

    private SecretKey signingKey;
    private JwtParser jwtParser;

    @PostConstruct
    void init() {
        if (jwtSecretB64 == null || jwtSecretB64.isBlank()) {
            throw new IllegalStateException("JWT secret must be configured and Base64-encoded");
        }
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretB64);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);

        this.jwtParser = Jwts.parserBuilder()
                .requireIssuer(issuer)
                .requireAudience(audience)
                .setSigningKey(signingKey)
                .setAllowedClockSkewSeconds(clockSkewSeconds)
                .build();
    }

    // ------------------ Generate Token ------------------
    public String generateToken(String email, User.Role role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setId(UUID.randomUUID().toString())
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(email)
                .claim("role", role.name())
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ------------------ Extract Email ------------------
    public String getEmailFromToken(String authorizationHeader) {
        return parseClaims(authorizationHeader).getSubject();
    }

    // ------------------ Extract Role ------------------
    public User.Role getRoleFromToken(String authorizationHeader) {
        Claims claims = parseClaims(authorizationHeader);
        String roleStr = claims.get("role", String.class);
        if (roleStr == null) return User.Role.USER; // default
        return User.Role.valueOf(roleStr);
    }

    // ------------------ Validate Token ------------------
    public boolean validateToken(String authorizationHeader) {
        try {
            parseClaims(authorizationHeader);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ------------------ Private Helpers ------------------
    private Claims parseClaims(String authorizationHeader) {
        String token = stripBearer(authorizationHeader);
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private String stripBearer(String token) {
        if (token == null) return null;
        String t = token.trim();
        if (t.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return t.substring(7).trim();
        }
        throw new IllegalArgumentException("Token must be prefixed with 'Bearer '");
    }
}

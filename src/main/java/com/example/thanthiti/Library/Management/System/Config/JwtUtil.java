package com.example.thanthiti.Library.Management.System.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")                  // Base64-encoded secret (no default!)
    private String jwtSecretB64;

    @Value("${jwt.expiration:3600000}")     // 1h by default; override in prod
    private long jwtExpirationInMs;

    @Value("${jwt.issuer:library-management-system}")     // set real issuer
    private String issuer;

    @Value("${jwt.audience:library-users}") // set real audience
    private String audience;

    @Value("${jwt.clockSkewSeconds:60}")    // tolerance for clock drift
    private long clockSkewSeconds;

    private SecretKey signingKey;
    private JwtParser jwtParser; // cached, thread-safe after build

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

    // Generate Token
    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                //.setHeaderParam("kid", "v1") // enable if you do key rotation
                .setId(UUID.randomUUID().toString()) // jti
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(email)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(expiry)
                // .claim("roles", List.of("USER")) // add your roles/claims here
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Email from Token
    public String getEmailFromToken(String token) {
        return jwtParser.parseClaimsJws(stripBearer(token))
                .getBody()
                .getSubject();
    }

    // Validate Token: signature + structure + iss/aud + exp/nbf
    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(stripBearer(token));
            return true;
        } catch (ExpiredJwtException e) {
            // log.warn("JWT expired", e);
            return false;
        } catch (UnsupportedJwtException e) {
            // log.warn("JWT unsupported", e);
            return false;
        } catch (MalformedJwtException e) {
            // log.warn("JWT malformed", e);
            return false;
        } catch (SecurityException e) { // includes SignatureException
            // log.warn("JWT signature invalid", e);
            return false;
        } catch (IllegalArgumentException e) {
            // log.warn("JWT illegal argument", e);
            return false;
        }
    }

    // Check if token is expired (after basic verification)
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(stripBearer(token)).getBody();
            return claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true; // treat invalid parse as expired/invalid
        }
    }

    private String stripBearer(String token) {
        if (token == null) return null;
        String t = token.trim();
        if (t.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return t.substring(7).trim();
        }
        return t;
    }
}

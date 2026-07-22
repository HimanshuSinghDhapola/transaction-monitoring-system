package com.fintech.transaction_monitoring_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(UserDetails userDetails){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getExpirationMs());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey())
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }

    public boolean isValid(String token, UserDetails userDetails){
        try{
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isExpired(token);
        }catch (JwtException | IllegalArgumentException e){
            log.debug("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public boolean isExpired(String token){
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey signingKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

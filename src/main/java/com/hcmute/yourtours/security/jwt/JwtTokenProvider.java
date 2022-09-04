package com.hcmute.yourtours.security.jwt;

import com.hcmute.yourtours.security.CustomUserDetails;
import com.hcmute.yourtours.security.properties.SecurityConfigurationProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenProvider {

    private final SecurityConfigurationProperties.Jwt jwtConfig;


    public JwtTokenProvider(SecurityConfigurationProperties securityProperties) {
        this.jwtConfig = securityProperties.getJwt();
    }


    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getJwtExpirationMs());
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(userDetails.getUser().getUserId().toString())
                .setIssuedAt(now)
                .claim("preferredUsername", userDetails.getUsername())
                .claim("sessionState", sessionId)
                .claim("role", userDetails.getUser().getRole())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

    public String generateRefreshJwtToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getRefreshJwtExpirationMs());

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }


    public UUID getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}

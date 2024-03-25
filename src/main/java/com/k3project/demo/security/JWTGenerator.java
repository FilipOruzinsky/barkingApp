package com.k3project.demo.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.k3project.demo.security.SecurityConstans.JWT_SECRET;

@Slf4j
@Component
public class JWTGenerator {

    Logger logger = LoggerFactory.getLogger(JWTGenerator.class);

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        logger.info("Generating JWT token for user :{}", username);
        try {
            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + SecurityConstans.JWT_EXPIRATION);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, SecurityConstans.JWT_SECRET)
                    .compact();
            logger.info("JWT token generated successfully for user: {}", username);
            return token;
        } catch (Exception e) {
            logger.error("Error occurred during JWT token generation for user '{}': {}", username, e.getMessage(), e);
            throw new RuntimeException("Error occurred during JWT token generation");
        } finally {
            logger.info("Method generateToken end");
        }
    }

    public String getUsernameFromJWT(String token) {
        logger.info("Extracting username from JWT token");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstans.JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            logger.info("Username '{}' extracted successfully from JWT token", username);
            return username;
        } catch (Exception e) {
            logger.error("Error occurred while extracting username from JWT token: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while extracting username from JWT token");
        } finally {
            logger.info("Method getUsernameFromJWT end");
        }
    }

    public boolean validateToken(String token) {
        logger.info("Validating JWT token");
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            logger.info("JWT token validated successfully");
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token validation failed: Token expired");
            return false;
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.warn("JWT token validation failed: {}", e.getMessage(), e);
            return false;
        }
    }
}

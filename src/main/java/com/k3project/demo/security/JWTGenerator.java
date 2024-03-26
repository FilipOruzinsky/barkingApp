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

@Component
public class JWTGenerator {

    Logger logger = LoggerFactory.getLogger(JWTGenerator.class);

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + SecurityConstans.JWT_EXPIRATION);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, SecurityConstans.JWT_SECRET)
                    .compact();
            logger.info("JWT token generated successfully for user: {} ", username);
            return token;
    }

    public String getUsernameFromJWT(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstans.JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            logger.info("Username '{}' extracted successfully from JWT token", username);
            return username;
    }

    public boolean validateToken(String token) {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            logger.info("JWT token validated successfully");
            return true;
    }
}

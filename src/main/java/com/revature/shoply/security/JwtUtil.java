package com.revature.shoply.security;

import com.revature.shoply.config.Parameters;
import com.revature.shoply.models.User;
import com.revature.shoply.models.enums.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static String secret;

    private static Key SECRET_KEY;

    @PostConstruct
    public void init() {
        this.secret = params.getSecret();
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }


    private final Parameters params;

    public JwtUtil(Parameters params) {
        this.params = params;
    }


    private static final long expiration = (long) 24 * 60 * 60 * 1000; // 1 day

    public static String generateToken(User user) {
        logger.info("Generating token for user: {}", user.getUsername());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            logger.info("Token is valid");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }

    public UserRole extractRole(String token) {
        String role = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().get("role", String.class);
        return UserRole.valueOf(role);
    }

}

package org.glgnn.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.glgnn.userservice.entity.User;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "mySecretKey12345";
    private static final long EXPIRATION = 3600000; // 1 saat

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
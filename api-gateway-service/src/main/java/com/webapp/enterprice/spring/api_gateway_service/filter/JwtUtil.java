package com.webapp.enterprice.spring.api_gateway_service.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * Validates the provided JWT token.
     *
     * @param token The JWT token to be validated.
     *              <p>
     *              This method parses and validates the given JWT token using the secret key. If the token is invalid,
     *              an exception will be thrown during the parsing process.
     */
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    /**
     * TODO aggiornare javadoc
     * @param token
     * @return
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * TODO aggiornare javadoc
     * @param token
     * @return
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * TODO aggiornare javadoc
     * @param token
     * @return
     */
    public Set<String> extractRole(String token) {
        try {

            Claims claims = extractAllClaims(token);

            // Extract the "roles" claim
            List<?> roles = claims.get("roles", List.class);

            // Handle null or missing roles
            if (roles == null)
                return Collections.emptySet();

            // Extract role names
            return roles.stream().map(Object::toString).collect(Collectors.toSet());

        } catch (Exception e) {
            System.out.println("Failed to extract roles from token: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    /**
     * Retrieves the signing key used to validate JWT tokens.
     *
     * @return The secret key used for HMAC-SHA signature validation.
     * <p>
     * This method decodes the base64 encoded secret key and returns it as a Key object suitable for
     * HMAC-SHA encryption. The secret key is sourced from the application properties.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

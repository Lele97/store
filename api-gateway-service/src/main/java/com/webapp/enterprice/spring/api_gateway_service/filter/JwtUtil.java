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
    private String secret;

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
     * Extracts all claims from the given JWT token.
     *
     * @param token The JWT token from which to extract claims.
     * @return Claims The claims extracted from the token.
     * <p>
     * This method parses the given JWT token and returns all the claims contained in it.
     * It uses the signing key to validate the token and extract the claims.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the email (subject) from the given JWT token.
     *
     * @param token The JWT token from which to extract the email.
     * @return String The email extracted from the token.
     * <p>
     * This method extracts the email (subject) from the claims contained in the given JWT token.
     * It calls the extractAllClaims method to get the claims and then retrieves the subject.
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts the roles from the given JWT token.
     *
     * @param token The JWT token from which to extract roles.
     * @return Set<String> A set of roles extracted from the token.
     * <p>
     * This method extracts the roles from the claims contained in the given JWT token.
     * It calls the extractAllClaims method to get the claims and then retrieves the "roles" claim.
     * If the roles claim is null or missing, it returns an empty set. If roles are present, it converts them to a set of strings.
     * If an exception occurs while extracting roles, it logs the error and returns an empty set.
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
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

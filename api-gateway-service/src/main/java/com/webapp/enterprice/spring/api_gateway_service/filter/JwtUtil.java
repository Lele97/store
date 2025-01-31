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

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }


    public Set<String> extractRole(String token) {
        try {
            Claims claims = extractAllClaims(token);
          Set<String> role_ = new HashSet<>();
            // Log the roles claim for debugging
            System.out.println("Roles claim in token: " + claims.get("roles"));

            // Extract the "roles" claim
            List<?> roles = claims.get("roles", List.class);

            // Handle null or missing roles
            if (roles == null) {
                System.out.println("No roles found in the token");
                return Collections.emptySet();
            }

            // Log the raw roles list for debugging
            System.out.println("Raw roles list: " + roles);

            long number_of_roles = roles.size();

            number_of_roles = roles.size();
            System.out.println("Number of roles: " + number_of_roles);

            // Extract role names
           role_ = roles.stream().map(Object::toString).collect(Collectors.toSet());
//                    .filter(role -> role instanceof Map) // Ensure the role is a Map
//                    .map(role -> (Map<?, ?>) role) // Cast to Map
//                    .filter(role -> role.containsKey("name")) // Filter out roles without a "name" field
//                    .map(role -> {
//                        Object name = role.get("name");
//                        return name != null ? name.toString() : null; // Convert to String if not null
//                    })
//                    .filter(Objects::nonNull) // Filter out null role names
//                    .collect(Collectors.toSet());

            System.out.println("Roles list: " + role_);

           return role_;
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

package com.webapp.enterprice.spring.api_gateway_service.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * Validates the provided JWT token.
     *
     * @param token The JWT token to be validated.
     *
     * This method parses and validates the given JWT token using the secret key. If the token is invalid,
     * an exception will be thrown during the parsing process.
     */
    public void validateToken(final String token){
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    /**
     * Retrieves the signing key used to validate JWT tokens.
     *
     * @return The secret key used for HMAC-SHA signature validation.
     *
     * This method decodes the base64 encoded secret key and returns it as a Key object suitable for
     * HMAC-SHA encryption. The secret key is sourced from the application properties.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

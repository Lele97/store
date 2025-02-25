package com.webapp.enterprice.spring.auth.service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.enterprice.spring.auth.service.config.CustomUserDetailsService;
import com.webapp.enterprice.spring.auth.service.exception.ErrorDetail;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService service;

    public JwtFilter(JwtService jwtService, CustomUserDetailsService service) {
        this.jwtService = jwtService;
        this.service = service;
    }

    /**
     * Filters incoming requests and processes the JWT authentication.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs during filtering
     *                          <p>
     *                          This method retrieves the Authorization header from the request, extracts the JWT token,
     *                          and validates it. If the token is valid and the authentication context is empty, it sets
     *                          the authentication in the SecurityContext. If the token is expired, it returns an error
     *                          response with a 401 Unauthorized status and a JSON error message.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractEmail(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            ErrorDetail error = new ErrorDetail();
            error.setErrorCode(String.valueOf(HttpStatus.UNAUTHORIZED));
            error.setErrorType(e.getMessage());
            error.setMessage("Expired JWT token");
            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(error));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}


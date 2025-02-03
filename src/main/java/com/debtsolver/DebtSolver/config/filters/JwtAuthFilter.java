package com.debtsolver.DebtSolver.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.debtsolver.DebtSolver.exception.TokenException;
import com.debtsolver.DebtSolver.service.CustomUserDetailsService;
import com.debtsolver.DebtSolver.service.TokenBlacklistService;
import com.debtsolver.DebtSolver.util.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestHeader = request.getHeader(Constants.AUTH_HEADER);

        String token = null;
        String email = null;

        if (requestHeader != null && requestHeader.startsWith(Constants.TOKEN_START)) {
            token = requestHeader.substring(Constants.TOKEN_START_LENGTH);

            if (tokenBlacklistService.isTokenOnBlacklist(token)) {
                throw new TokenException("Invalid Token", HttpStatus.UNAUTHORIZED);
            }

            try {
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                        .build()
                        .verify(token);

                if (decodedJWT.getExpiresAt() != null && decodedJWT.getExpiresAt().before(new Date())) {
                    throw new TokenException("Token has expired", HttpStatus.UNAUTHORIZED);
                }

                email = decodedJWT.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new TokenException("Invalid Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
            }

        }
        filterChain.doFilter(request, response);
    }
}

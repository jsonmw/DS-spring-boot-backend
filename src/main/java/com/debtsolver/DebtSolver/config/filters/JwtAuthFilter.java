package com.debtsolver.DebtSolver.config.filters;

import com.debtsolver.DebtSolver.exception.AuthException;
import com.debtsolver.DebtSolver.exception.TokenException;
import com.debtsolver.DebtSolver.util.Constants;
import com.debtsolver.DebtSolver.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom filter for authenticating JWT
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestHeader = request.getHeader(Constants.AUTH_HEADER);

        if (requestHeader != null && requestHeader.startsWith(Constants.TOKEN_START)) {
            String token = requestHeader.substring(Constants.TOKEN_START_LENGTH);

            try {

                String email = jwtUtil.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (TokenException e) {
                throw e;
            } catch (Exception e) {
                throw new AuthException(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}

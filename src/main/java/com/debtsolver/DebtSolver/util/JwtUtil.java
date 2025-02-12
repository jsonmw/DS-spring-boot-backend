package com.debtsolver.DebtSolver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.debtsolver.DebtSolver.exception.TokenException;
import com.debtsolver.DebtSolver.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {


    private final TokenBlacklistService tokenBlacklistService;

    @Value("${jwt.secret}")
    private String secret;


    /**
     * Returns the user associated with a given token
     *
     * @param token : String representation of token
     * @return String containing username
     */
    public String getUsernameFromToken(String token) {
        return getDecodedJwt(token).getSubject();
    }

//    /**
//     * Check if given token is expired
//     *
//     * @param token: String representation of the token
//     * @return boolean : true: expired, false: valid
//     */
//    public boolean isTokenExpired(String token) {
//        DecodedJWT decodedJWT = getDecodedJwt(token);
//        return );
//    }

    /**
     * Create new JWT with Auth0
     *
     * @param email : the email of the associated user
     * @return String representation of the new JWT
     */
    public String generateToken(String email) {

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ONE_HOUR_EXPIRATION))
                .sign(Algorithm.HMAC512(secret));
    }

    /**
     * Extracts the JWT from a Request Header, or returns null if none exists
     *
     * @param request : The HTTP request that may or may not contain the token
     * @return String containing the token or null
     */
    public String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(Constants.AUTH_HEADER);
        if (header !=null && header.startsWith(Constants.TOKEN_START)) {
            return header.substring(Constants.TOKEN_START_LENGTH);
        }
        return null;
    }


    /**
     * Utility method to decode a JWT using Auth0.
     *
     * @param token : A String representation of the token
     * @return DecodedJWT object
     */
    private DecodedJWT getDecodedJwt(String token) {
        if (tokenBlacklistService.isTokenOnBlacklist(token)) {
            throw new TokenException("Invalid Token");
        }

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token);

            if ((decodedJWT.getExpiresAt() != null && decodedJWT.getExpiresAt().before(new Date()))) {
                throw new TokenException("Token has expired");
            }

            return decodedJWT;

        } catch (JWTVerificationException e) {
            throw new TokenException("Invalid Token: " + e.getMessage());
        }
    }
}

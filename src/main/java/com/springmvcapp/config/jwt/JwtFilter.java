package com.springmvcapp.config.jwt;

import com.springmvcapp.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTH_TYPE = "Bearer ";
    private static final int TOKEN_START = 7;

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getTokenFromRequest(request);
        if (token != null) {
            var loginFromToken = jwtProvider.getLoginFromToken(token);
            var userDetails = customUserDetailsService.loadUserByUsername(loginFromToken);
            var auth = UsernamePasswordAuthenticationToken
                    .authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        var bearer = request.getHeader(AUTHORIZATION_HEADER);
        if (validateBearer(bearer)) {
            return bearer.substring(TOKEN_START);
        }
        return null;
    }

    private boolean validateBearer(String bearer) {
        return hasText(bearer) && bearer.startsWith(AUTH_TYPE);
    }
}

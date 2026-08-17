package com.matchskills.jobapplication.service.configurations;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.matchskills.jobapplication.service.enums.RoleType;
import com.matchskills.jobapplication.service.jwt.InternalTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class InternalAuthFilter extends OncePerRequestFilter {

    private final InternalTokenValidator validator;

    public InternalAuthFilter(InternalTokenValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("X-Internal-Token");

        if (header != null) {
            try {
                DecodedJWT decoded = validator.validate(header);
                String serviceName = decoded.getSubject();

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + RoleType.System.name()));
                var auth = new UsernamePasswordAuthenticationToken(serviceName, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (JWTVerificationException e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
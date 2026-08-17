package com.matchskills.jobapplication.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InternalTokenProvider {

    @Value("${jwt.internal.secret}")
    private String secret;

    public String generate(String serviceName) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(serviceName)
                .withClaim("role", "SYSTEM")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(15))
                .sign(algorithm);
    }
}
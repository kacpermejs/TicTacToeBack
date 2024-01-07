package com.tic.tac.tictactoeback.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CognitoAuthenticationService {

    private final NimbusJwtDecoder jwtDecoder;

    public CognitoAuthenticationService(NimbusJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public Authentication authenticate(String token) {
        Jwt jwt = parseJwt(token);

        return new JwtAuthenticationToken(jwt);
    }

    private Jwt parseJwt(String token) {
        return jwtDecoder.decode(token);
    }
}

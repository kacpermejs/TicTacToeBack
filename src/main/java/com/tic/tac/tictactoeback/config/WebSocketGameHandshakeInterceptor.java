package com.tic.tac.tictactoeback.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.tic.tac.tictactoeback.services.CognitoAuthenticationService;

public class WebSocketGameHandshakeInterceptor implements HandshakeInterceptor {
    @Autowired
    private CognitoAuthenticationService authenticationService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Extract the token from the HTTP headers
        String token = request.getHeaders().getFirst("Authorization");

        // Authenticate the user
        if (token != null) {
            authenticationService.authenticate(token);
            // You may also store additional attributes in the 'attributes' map if needed
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            @Nullable Exception exception) {
        // TODO Auto-generated method stub
        
    }
}




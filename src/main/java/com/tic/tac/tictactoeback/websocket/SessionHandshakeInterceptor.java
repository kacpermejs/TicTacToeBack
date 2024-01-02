package com.tic.tac.tictactoeback.websocket;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class SessionHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpHeaders headers = servletRequest.getHeaders();
            
            // Extract headers and store them in the attributes map
            attributes.put("Your-Custom-Header", headers.getFirst("Your-Custom-Header"));
            // Add more headers if needed
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, 
        ServerHttpResponse response, WebSocketHandler wsHandler, 
        @Nullable Exception exception) {
        // TODO Auto-generated method stub
        
    }
}


package com.tic.tac.tictactoeback.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;


public class WebSocketAuthInterceptor implements ChannelInterceptor  {


   @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        //     // Extract the token from the StompHeaderAccessor (you may need to adapt this)
        //     String token = accessor.getFirstNativeHeader("Authorization");

        //     // Set the security context manually
        //     Authentication authentication = authenticationService.authenticate(token);
        //     System.out.println("authentication: ");
        //     System.out.println(authentication);
        //     SecurityContextHolder.getContext().setAuthentication(authentication);
        // }

        return message;
    }
}

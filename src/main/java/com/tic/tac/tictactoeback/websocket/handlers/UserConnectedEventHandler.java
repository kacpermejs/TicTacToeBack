package com.tic.tac.tictactoeback.websocket.handlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
public class UserConnectedEventHandler {
    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {

        System.out.println("User connected:");
        System.out.println(event);
        
    }

}

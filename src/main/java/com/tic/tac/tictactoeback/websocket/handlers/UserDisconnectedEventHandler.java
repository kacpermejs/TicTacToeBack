package com.tic.tac.tictactoeback.websocket.handlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.tic.tac.tictactoeback.managers.LobbyManager;

@Component
public class UserDisconnectedEventHandler {
    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        
        // Example: Remove user from the queue
        var entry = LobbyManager.removeFromQueueWithSessionId(sessionId);
        System.out.println("User disconnected, sessionId: " + sessionId);
        System.out.println(entry);
        LobbyManager.printQueue();
        
    }
}

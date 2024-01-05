package com.tic.tac.tictactoeback.websocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tic.tac.tictactoeback.managers.LobbyManager;
import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.models.QueueEntry;
import com.tic.tac.tictactoeback.services.GameService;

@Controller
public class LobbyController {

    public record GameFoundMessage(Long opponentId, GameSession gameSession) {}
    public record JoinConfirmationMessage(String message) {}

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    GameService gameService;

    @MessageMapping("/lobby/join")
    public void enterQueue(@Payload Long playerId, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        LobbyManager.addUserToQueue(new QueueEntry(playerId, sessionId));
        System.out.println("player " + playerId + " joined. ");
        sendJoinConfirmation(playerId);
        
        LobbyManager.tryMatchPlayers( (Long playerOneId, Long playerTwoId) -> {
            var session = gameService.createGameSession(playerOneId, playerTwoId);
            
            //Send message to both players
            GameFoundMessage message1 = new GameFoundMessage(playerTwoId, session);
            GameFoundMessage message2 = new GameFoundMessage(playerOneId, session);
            sendGameFound(playerOneId, message1);
            sendGameFound(playerTwoId, message2);
        });
    }

    public void sendJoinConfirmation(Long userId) {
        JoinConfirmationMessage confirmationMessage = new JoinConfirmationMessage("You have joined the queue.");
        simpMessagingTemplate.convertAndSendToUser(
            String.valueOf(userId),
            "/queue/join-confirmation",
            confirmationMessage
        );
    }

    public void sendGameFound(Long userId, GameFoundMessage gameFoundMessage) {
        simpMessagingTemplate.convertAndSendToUser(
            String.valueOf(userId), // Convert Long to String
            "/queue/game-found",
            gameFoundMessage
        );
    }

}

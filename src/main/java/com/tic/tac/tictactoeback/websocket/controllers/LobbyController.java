package com.tic.tac.tictactoeback.websocket.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.tic.tac.tictactoeback.managers.LobbyManager;
import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.models.QueueEntry;
import com.tic.tac.tictactoeback.repositories.CognitoUserDetailRepository;
import com.tic.tac.tictactoeback.services.CognitoUserMappingService;
import com.tic.tac.tictactoeback.services.GameService;

@Controller
public class LobbyController {

    public record GameFoundMessage(Long opponentId, GameSession gameSession) {}
    public record JoinConfirmationMessage(String message) {}
    public record EnterQueuePayload(String userId) {}

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CognitoUserMappingService cognitoUserMappingService;

    @Autowired
    GameService gameService;

    @MessageMapping("/lobby/join")
    public void enterQueue(@Payload EnterQueuePayload payload, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String currentCognitoUserId = payload.userId;

        Long playerId = cognitoUserMappingService.findInternalId(currentCognitoUserId);
        LobbyManager.addUserToQueue(new QueueEntry(playerId, sessionId));
        System.out.println("player " + playerId + " joined. ");
        sendJoinConfirmation(currentCognitoUserId);
        
        LobbyManager.tryMatchPlayers( (Long playerOneId, Long playerTwoId) -> {
            var session = gameService.createGameSession(playerOneId, playerTwoId);
            
            //Send message to both players
            GameFoundMessage message1 = new GameFoundMessage(playerTwoId, session);
            GameFoundMessage message2 = new GameFoundMessage(playerOneId, session);

            String playerOneCognitoId = cognitoUserMappingService.findCognitoId(playerOneId);
            String playerTwoCognitoId = cognitoUserMappingService.findCognitoId(playerTwoId);

            sendGameFound(playerOneCognitoId, message1);
            sendGameFound(playerTwoCognitoId, message2);
        });
    }

    public void sendJoinConfirmation(String userId) {
        JoinConfirmationMessage confirmationMessage = new JoinConfirmationMessage("You have joined the queue.");
        simpMessagingTemplate.convertAndSendToUser(
            userId,
            "/queue/join-confirmation",
            confirmationMessage
        );
    }

    public void sendGameFound(String userId, GameFoundMessage gameFoundMessage) {
        simpMessagingTemplate.convertAndSendToUser(
            userId,
            "/queue/game-found",
            gameFoundMessage
        );
    }

}

package com.tic.tac.tictactoeback.websocket.controllers;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tic.tac.tictactoeback.services.GameService;

@Controller
public class LobbyController {

    public record GameFoundMessage(Long opponentId, Long gameSessionId) {}
    public record JoinConfirmationMessage(String message) {}

    // List of player ids
    private Queue<Long> lobby = new LinkedList<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    GameService gameService;

    @MessageMapping("/lobby/join")
    public void enterQueue(Long playerId) {
        
        lobby.add(playerId);
        System.out.println("player " + playerId + " joined. Queue size: " + lobby.size());
        sendJoinConfirmation(playerId);

        if (lobby.size() >= 2) {
            Long playerOneId = lobby.poll();
            Long playerTwoId = lobby.poll();

            var session = gameService.createGameSession(playerOneId, playerTwoId);
            
            //Send message to both players
            GameFoundMessage message1 = new GameFoundMessage(playerTwoId, session.getId());
            GameFoundMessage message2 = new GameFoundMessage(playerOneId, session.getId());
            sendGameFound(playerOneId, message1);
            sendGameFound(playerTwoId, message2);
        }
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

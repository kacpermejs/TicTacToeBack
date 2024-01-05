package com.tic.tac.tictactoeback.websocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tic.tac.tictactoeback.managers.GameManager;
import com.tic.tac.tictactoeback.models.GameBoard;
import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.services.GameService;
import com.tic.tac.tictactoeback.websocket.payload.MoveCommand;

@Controller
public class GameSessionController {
    @Autowired
    private GameManager gameManager;

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/move")
    public void makeMove(@Payload MoveCommand moveCommand) {
        Long sessionId = moveCommand.sessionId();
        Long movingPlayerId = moveCommand.playerId();
        GameSession session = gameService.getSessionWithId(sessionId);

        char playersShape = movingPlayerId == session.getPlayer1().getId() 
            ? session.getPlayerOneShape()
            : session.getPlayerTwoShape();

        GameBoard updatedGameBoard = gameManager.makeMove(moveCommand, playersShape);

        if (updatedGameBoard == null) { //move not valid
            return;
        }
        // Assuming each player has a unique username (you might want to adapt this based on your user model)

        Long playerOneId = session.getPlayer1().getId();
        Long playerTwoId = session.getPlayer2().getId();

        // Send the updated game to both players
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(playerOneId), "/playing/update", updatedGameBoard);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(playerTwoId), "/playing/update", updatedGameBoard);

    }

    // @MessageMapping("/in-game-presence")
    // public void checkPresence() {
    //     //TODO
    // }
}

package com.tic.tac.tictactoeback.websocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tic.tac.tictactoeback.managers.GameManager;
import com.tic.tac.tictactoeback.models.GameBoard;
import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.services.CognitoUserMappingService;
import com.tic.tac.tictactoeback.services.GameService;
import com.tic.tac.tictactoeback.services.RankingService;
import com.tic.tac.tictactoeback.websocket.payload.MoveCommand;

@Controller
public class GameSessionController {
    @Autowired
    private GameManager gameManager;

    @Autowired
    private GameService gameService;

    @Autowired
    private CognitoUserMappingService cognitoUserMappingService;

    @Autowired
    RankingService rankingService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/move")
    public void makeMove(@Payload MoveCommand moveCommand, SimpMessageHeaderAccessor accessor) {
        Long sessionId = moveCommand.sessionId();
        String currentCognitoUserId = moveCommand.playerId();
        System.out.println("current cognito Id: ");
        System.out.println(currentCognitoUserId);
        Long movingPlayerId = cognitoUserMappingService.findInternalId(currentCognitoUserId);;

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

        String playerOneCognitoId = cognitoUserMappingService.findCognitoId(playerOneId);
        String playerTwoCognitoId = cognitoUserMappingService.findCognitoId(playerTwoId);

        // Send the updated game to both players
        simpMessagingTemplate.convertAndSendToUser(playerOneCognitoId, "/playing/update", updatedGameBoard);
        simpMessagingTemplate.convertAndSendToUser(playerTwoCognitoId, "/playing/update", updatedGameBoard);

        if(updatedGameBoard.gameEnded()) {

            rankingService.updateOnGameResult(session, updatedGameBoard);
        }
    }

    // @MessageMapping("/in-game-presence")
    // public void checkPresence() {
    //     //TODO
    // }
}

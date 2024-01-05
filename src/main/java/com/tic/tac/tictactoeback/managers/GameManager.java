package com.tic.tac.tictactoeback.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.tic.tac.tictactoeback.models.GameBoard;
import com.tic.tac.tictactoeback.websocket.payload.MoveCommand;

@Component
public class GameManager {
    public static final Map<Long, GameBoard> games = new ConcurrentHashMap<>();

    public GameBoard makeMove(MoveCommand moveCommand, char playersShape) {
        Long sessionId = moveCommand.sessionId();
        GameBoard gameBoard = games.get(sessionId);

        if (gameBoard == null) {
            return null;
        }

        if (gameBoard.getCurrentPlayer() != playersShape) {
            return null;
        }

        // Update the game board based on the move command
        // You need to implement the logic in your GameBoard class
        boolean success = gameBoard.makeMove(moveCommand.row(), moveCommand.column());

        // Broadcasting the updated game board to all subscribed clients
        // This can be enhanced to send updates only to the specific client who made the move
        if (success)
            return gameBoard;
        else
            return null;
    }

    public void newGame(Long sessionId) {
        games.put(sessionId, new GameBoard());
        System.out.println("NewGame");
        System.out.println(games);
    }
}

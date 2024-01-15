package com.tic.tac.tictactoeback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tic.tac.tictactoeback.models.GameBoard;
import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.models.GameSession.GameResult;
import com.tic.tac.tictactoeback.repositories.GameSessionRepository;
import com.tic.tac.tictactoeback.repositories.RankingRepository;
import com.tic.tac.tictactoeback.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RankingService {

    @Autowired
    GameSessionRepository gameSessionRepository;

    @Autowired
    RankingRepository rankingRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void updateOnGameResult(GameSession gameSession, GameBoard updatedGameBoard) {

        GameResult result = GameResult.Unfinished;
        switch (updatedGameBoard.getWinner()) {
            case 'X':
                if (gameSession.getPlayerOneShape() == 'X') result = GameResult.PlayerOneWon;
                else if (gameSession.getPlayerTwoShape() == 'X') result = GameResult.PlayerTwoWon;
                break;
            case 'O':
                if (gameSession.getPlayerOneShape() == 'O') result = GameResult.PlayerOneWon;
                else if (gameSession.getPlayerTwoShape() == 'O') result = GameResult.PlayerTwoWon;
                break;
        
            default:
                result = GameResult.Draw;
                break;
        }
        gameSession.setGameResult(result);

        var playerOne = gameSession.getPlayer1();
        var playerTwo = gameSession.getPlayer2();

        var currentPlayerOneRanking = playerOne.getRanking();
        var currentPlayerTwoRanking = playerTwo.getRanking();

        int playerOneScore = currentPlayerOneRanking.getScore();
        int playerTwoScore = currentPlayerTwoRanking.getScore();

        switch (result) {
            case GameSession.GameResult.PlayerOneWon:
                currentPlayerOneRanking.matchWon();
                currentPlayerOneRanking.updateScoreWin(playerTwoScore);
                currentPlayerTwoRanking.matchLost();
                currentPlayerTwoRanking.updateScoreLost(playerOneScore);
                break;
            case GameSession.GameResult.PlayerTwoWon:
                currentPlayerOneRanking.matchLost();
                currentPlayerOneRanking.updateScoreLost(playerTwoScore);
                currentPlayerTwoRanking.matchWon();
                currentPlayerTwoRanking.updateScoreWin(playerOneScore);
                break;
            case GameSession.GameResult.Draw:
                currentPlayerOneRanking.updateScoreDraw(playerTwoScore);
                currentPlayerTwoRanking.updateScoreDraw(playerOneScore);
                break;
        
            default:
                break;
        }

        gameSessionRepository.save(gameSession);
        rankingRepository.save(currentPlayerOneRanking);
        rankingRepository.save(currentPlayerTwoRanking);
    }
}

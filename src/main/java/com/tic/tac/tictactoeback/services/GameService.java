package com.tic.tac.tictactoeback.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tic.tac.tictactoeback.managers.GameManager;
import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.models.UserDetails;
import com.tic.tac.tictactoeback.repositories.GameSessionRepository;
import com.tic.tac.tictactoeback.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class GameService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired 
    private GameManager gameManager;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public GameSession createGameSession(Long playerOneId, Long playerTwoId) {

        boolean playerOneCircle = generateRandomBoolean();
        
        UserDetails playerOne = userRepository.findById(playerOneId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player one not found!"));
        UserDetails playerTwo = userRepository.findById(playerTwoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player two not found!"));
        GameSession session = GameSession
            .builder()
            .player1(playerOne)
            .player2(playerTwo)
            .playerOneShape(playerOneCircle ? 'O' : 'X')
            .playerTwoShape(playerOneCircle ? 'X' : 'O')
            .gameResult(GameSession.GameResult.Unfinished)
            .build();

        var savedSession = gameSessionRepository.save(session);
        gameManager.newGame(savedSession.getId());

        return savedSession;
    }

    public void endGameSessionWithId(Long sessionId) {
        gameSessionRepository.deleteById(sessionId);
    }

    public GameSession getSessionWithId(Long sessionId) {
        return gameSessionRepository.findById(sessionId).orElse(null);
    }

    private static boolean generateRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
    
}

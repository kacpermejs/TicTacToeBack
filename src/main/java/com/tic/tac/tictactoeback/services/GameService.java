package com.tic.tac.tictactoeback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.models.User;
import com.tic.tac.tictactoeback.repositories.GameSessionRepository;
import com.tic.tac.tictactoeback.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class GameService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public GameSession createGameSession(Long playerOneId, Long playerTwoId) {
        
        User playerOne = userRepository.findById(playerOneId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player one not found!"));
        User playerTwo = userRepository.findById(playerTwoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player two not found!"));
        GameSession session = GameSession
            .builder()
            .player1(playerOne)
            .player2(playerTwo).build();
        

        return gameSessionRepository.save(session);
    }

    public void endGameSessionWithId(Long sessionId) {
        gameSessionRepository.deleteById(sessionId);
    }
    
}

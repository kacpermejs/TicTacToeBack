package com.tic.tac.tictactoeback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tic.tac.tictactoeback.models.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

}

package com.tic.tac.tictactoeback.repositories;


import com.tic.tac.tictactoeback.models.UserDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails, Long> {
    List<UserDetails> findAllByOrderByRanking_ScoreDesc();
}

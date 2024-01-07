package com.tic.tac.tictactoeback.repositories;


import com.tic.tac.tictactoeback.models.UserDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
    List<UserDetail> findAllByOrderByRanking_ScoreDesc();
}

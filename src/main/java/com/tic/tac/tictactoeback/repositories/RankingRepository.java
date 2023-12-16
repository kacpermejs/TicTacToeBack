package com.tic.tac.tictactoeback.repositories;


import com.tic.tac.tictactoeback.models.Ranking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    
}

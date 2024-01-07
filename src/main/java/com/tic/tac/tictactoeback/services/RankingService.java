package com.tic.tac.tictactoeback.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tic.tac.tictactoeback.models.Ranking;
import com.tic.tac.tictactoeback.models.UserDetail;
import com.tic.tac.tictactoeback.repositories.RankingRepository;
import com.tic.tac.tictactoeback.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RankingService {

    @Autowired
    RankingRepository rankingRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void updateScore(Long userId, int scoreOffset) {
        Optional<UserDetail> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            Ranking ranking = user.getRanking();

            ranking.setScore(ranking.getScore() + scoreOffset);
            rankingRepository.save(ranking);
        });
    }
}

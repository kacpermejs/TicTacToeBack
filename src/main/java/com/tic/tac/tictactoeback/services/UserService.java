package com.tic.tac.tictactoeback.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tic.tac.tictactoeback.models.Ranking;
import com.tic.tac.tictactoeback.models.UserDetail;
import com.tic.tac.tictactoeback.repositories.RankingRepository;
import com.tic.tac.tictactoeback.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RankingRepository rankingRepository;

    public List<UserDetail> findAll() {
        return userRepository.findAll();
    }

    public List<UserDetail> findAllRanked() {
        return userRepository.findAllByOrderByRanking_ScoreDesc();
    }

    public UserDetail findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        UserDetail user = userRepository
            .findById(userId)
            .orElseThrow(() -> 
                new EntityNotFoundException("User not found with id: " + userId));

        userRepository.delete(user);
    }

    @Transactional
    public UserDetail createUserWithRanking(UserDetail user) {
        Ranking ranking = new Ranking();
        var random = new Random();
        ranking.setScore(random.nextInt(101));
        rankingRepository.save(ranking);

        user.setRanking(ranking); // Set the relationship
        return userRepository.save(user);
    }
}

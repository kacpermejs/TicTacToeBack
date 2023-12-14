package com.tic.tac.tictactoeback.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tic.tac.tictactoeback.models.Ranking;
import com.tic.tac.tictactoeback.repositories.RankingRepository;

@RestController
@CrossOrigin
@RequestMapping("/ranking")
public class RankingController {
    
    @Autowired
    private RankingRepository rankingRepository;

    @GetMapping("/")
    List<Ranking> getAllRankings() {
        return rankingRepository.findAll();
    }

    @GetMapping("/{id}")
    Ranking getRanking(@PathVariable Long id) {
        return rankingRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    Ranking getRanking(@RequestBody Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    @PutMapping("/{id}")
    Ranking getRanking(@PathVariable Long id, @RequestBody Ranking ranking) {
        Ranking oldRanking = rankingRepository.findById(id).orElse(null);
        oldRanking.setMatchesWon(ranking.getMatchesWon());
        oldRanking.setMatchesLost(ranking.getMatchesLost());
        oldRanking.setRankingPoints(ranking.getRankingPoints());
        
        return rankingRepository.save(oldRanking);
    }

    @DeleteMapping("/{id}")
    Long deleteRanking(@PathVariable Long id) {
        rankingRepository.deleteById(id);
        return id;
    }
}

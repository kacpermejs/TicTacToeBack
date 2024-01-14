package com.tic.tac.tictactoeback.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tic.tac.tictactoeback.models.UserDetails;
import com.tic.tac.tictactoeback.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
public class RankingController {

    public record RankingDTO(String nickname, int score, float winRate) {}

    @Autowired
    UserService userService;

    @GetMapping("/public/ranking")
    public ResponseEntity<List<UserDetails>> getMethodName() {
        var userDetails = userService.findAllRanked();

        // List<RankingDTO> rankingDTOList = userDetails.stream()
        //         .map(userDetail -> {

        //             var nickname = userDetail.getName();
        //             var ranking = userDetail.getRanking();
        //             var score = ranking.getScore();
        //             var winRate = (float) ranking.getMatchesWon() / ranking.getMatchesLost();

        //             return new RankingDTO(nickname, score, winRate);
        //         })
        //         .collect(Collectors.toList());

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }    
}

package com.tic.tac.tictactoeback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tic.tac.tictactoeback.services.RankingService;

@RestController
@CrossOrigin
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    RankingService rankingService;

    @PostMapping("/updateScore")
    public ResponseEntity<String> updateScore(@RequestParam Long userId, @RequestParam int newScore) {
        rankingService.updateScore(userId, newScore);
        return new ResponseEntity<>("Score updated successfully", HttpStatus.OK);
    }
    
}

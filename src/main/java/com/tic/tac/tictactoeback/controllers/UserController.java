package com.tic.tac.tictactoeback.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tic.tac.tictactoeback.models.UserDetails;
import com.tic.tac.tictactoeback.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    ResponseEntity<List<UserDetails>> getAllUsers() {
        var users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/ranked")
    ResponseEntity<List<UserDetails>> getAllUsersRanked() {
        var rankedUsers = userService.findAllRanked();
        return new ResponseEntity<>(rankedUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDetails> getUser(@PathVariable Long id) {
        UserDetails user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDetails> createUserWithRanking(@RequestBody UserDetails user) {
        UserDetails savedUser = userService.createUserWithRanking(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User and its Ranking deleted successfully", HttpStatus.OK);
    }
}

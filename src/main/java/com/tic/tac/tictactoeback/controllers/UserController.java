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

import com.tic.tac.tictactoeback.models.User;
import com.tic.tac.tictactoeback.repositories.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    User getUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    User getUser(@PathVariable String id, @RequestBody User user) {
        User oldUser = userRepository.findById(id).orElse(null);
        oldUser.setName(user.getName());

        return userRepository.save(oldUser);
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return id;
    }
}

package com.tic.tac.tictactoeback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tic.tac.tictactoeback.config.UserAuthenticationFilter;
import com.tic.tac.tictactoeback.models.CognitoUserDetail;

@RestController
@CrossOrigin
@RequestMapping("")
public class TestController {

    private record TestMessage(String message) {}
    private record TestPrivateMessage(String message, CognitoUserDetail user) {}

    @GetMapping("/public/hello")
    public TestMessage publicHello() {
        String message = "Hello, this is a public resource!";
        return new TestMessage(message);
    }

    @GetMapping("/private/hello")
    public TestPrivateMessage privateHello(@RequestAttribute(name = UserAuthenticationFilter.CURRENT_USER_ATTRIBUTE) CognitoUserDetail user) {
        String message = "Hello, this is a private resource!";
        
        return new TestPrivateMessage(message, user);
    }

    @GetMapping
    public ResponseEntity<TestMessage> healthCheck() {
        return ResponseEntity.ok().body(new TestMessage("OK"));
    }

}

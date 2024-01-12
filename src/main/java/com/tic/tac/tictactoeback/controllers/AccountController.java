package com.tic.tac.tictactoeback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tic.tac.tictactoeback.models.UserDetails;
import com.tic.tac.tictactoeback.models.UserDetailsDTO;
import com.tic.tac.tictactoeback.services.CognitoUserMappingService;
import com.tic.tac.tictactoeback.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

    private record AccountControllerResponse(String message, UserDetails user) {}
    
    @Autowired
    private CognitoUserMappingService cognitoUserMappingService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AccountControllerResponse> registerUserDetails(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDetailsDTO userDTO) {

        String userId = jwt.getSubject();

        UserDetails user = UserDetails.builder()
            .name(userDTO.name())
            .build();

        var savedUser = userService.createUserWithRanking(user);

        cognitoUserMappingService.createMapping(savedUser, userId);
        
        return new ResponseEntity<>(new AccountControllerResponse("User authentication record created successfully", savedUser), HttpStatus.OK);
    }
}

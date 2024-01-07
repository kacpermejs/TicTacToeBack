package com.tic.tac.tictactoeback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.tic.tac.tictactoeback.repositories.CognitoUserDetailRepository;

@Service
public class CognitoUserMappingService {
    @Autowired
    private CognitoUserDetailRepository cognitoUserDetailRepository;

    public Long findInternalId(String cognitoId) {
        return cognitoUserDetailRepository
            .findById(cognitoId)
            .orElseThrow(() -> new NotFoundException("No user mapping!"))
            .getUserDetailId();
    }

    public String findCognitoId(Long internalId) {
        return cognitoUserDetailRepository
            .findByUserDetailId(internalId)
            .orElseThrow(() -> new NotFoundException("No user mapping!"))
            .getCognitoUserId();
    }
}

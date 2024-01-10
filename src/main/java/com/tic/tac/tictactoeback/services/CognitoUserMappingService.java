package com.tic.tac.tictactoeback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.tic.tac.tictactoeback.models.CognitoUserDetail;
import com.tic.tac.tictactoeback.models.UserDetail;
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

    public void createMapping(UserDetail userDetail, String cognitoId) {
        CognitoUserDetail record = CognitoUserDetail.builder()
            .cognitoUserId(cognitoId)
            .userDetailId(userDetail.getId())
            .build();

        cognitoUserDetailRepository.save(record);
    }

    public void registerNewUser(String cognitoId) {
        
    }

    
}

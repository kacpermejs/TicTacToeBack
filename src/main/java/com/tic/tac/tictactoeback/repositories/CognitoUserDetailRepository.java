package com.tic.tac.tictactoeback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tic.tac.tictactoeback.models.CognitoUserDetail;
import java.util.Optional;


public interface CognitoUserDetailRepository extends JpaRepository<CognitoUserDetail, String> {
    Optional<CognitoUserDetail> findByUserDetailId(Long userDetailId);
}

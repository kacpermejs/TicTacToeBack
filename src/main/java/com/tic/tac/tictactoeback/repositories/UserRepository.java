package com.tic.tac.tictactoeback.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tic.tac.tictactoeback.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    
}

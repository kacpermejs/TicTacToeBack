package com.tic.tac.tictactoeback.repositories;


import com.tic.tac.tictactoeback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}

package com.tic.tac.tictactoeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tic.tac.tictactoeback.models.GameSession;
import com.tic.tac.tictactoeback.models.User;
import com.tic.tac.tictactoeback.services.GameService;
import com.tic.tac.tictactoeback.services.UserService;

import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TicTacToeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeBackApplication.class, args);
		//ConfigurableApplicationContext context = SpringApplication.run(TicTacToeBackApplication.class, args);

		//GameService gameSessionService = context.getBean(GameService.class);
        //GameSession createdGameSession = gameSessionService.createGameSession(1L, 2L);
	
		//context.close();
	}

}

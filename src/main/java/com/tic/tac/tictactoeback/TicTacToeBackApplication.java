package com.tic.tac.tictactoeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.tic.tac.tictactoeback.config.ExampleSecret;

@SpringBootApplication
@EnableConfigurationProperties(ExampleSecret.class)
public class TicTacToeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeBackApplication.class, args);
	}

}

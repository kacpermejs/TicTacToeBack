package com.tic.tac.tictactoeback.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("secrets")
public record ExampleSecret(String SomeSecretApiKey) {

}

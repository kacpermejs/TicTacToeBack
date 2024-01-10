package com.tic.tac.tictactoeback.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.tic.tac.tictactoeback.config.WebSocketGameHandshakeInterceptor;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  // @Override
  // public void configureClientInboundChannel(ChannelRegistration registration) {
  //     registration.interceptors(new WebSocketAuthInterceptor());
  // }
  
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic", "/queue", "/playing", "/user");
    registry.setApplicationDestinationPrefixes("/game");
    registry.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket")
        .setAllowedOriginPatterns("*")
        .addInterceptors(new WebSocketGameHandshakeInterceptor())
        .withSockJS()
        .setHeartbeatTime(1_000);
  }
}
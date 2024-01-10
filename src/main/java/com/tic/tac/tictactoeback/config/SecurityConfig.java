package com.tic.tac.tictactoeback.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${cognito.issuer-uri}")
    private String issuerUri;

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .addFilterAfter(userAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .authorizeHttpRequests((httpRequestsAuthorizer) -> 
                    httpRequestsAuthorizer
                        .requestMatchers(HttpMethod.GET, "/faq").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/websocket/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagementConfigurer ->
                    sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oAuth2ResourceServerConfigurerCustomizer())
                .build();
    }

    public Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oAuth2ResourceServerConfigurerCustomizer() {
        final JwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuerUri);

        return (resourceServerConfigurer) -> 
            resourceServerConfigurer
                .jwt(jwtConfigurer -> jwtConfigurer.decoder(decoder));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
				//FIXME wymaga konfiguracji
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}
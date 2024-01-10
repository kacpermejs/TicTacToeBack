package com.tic.tac.tictactoeback.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tic.tac.tictactoeback.models.CognitoUserDetail;
import com.tic.tac.tictactoeback.repositories.CognitoUserDetailRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class UserAuthenticationFilter extends OncePerRequestFilter {
    public static final String CURRENT_USER_ATTRIBUTE = "currentUser";

    @Autowired
    private CognitoUserDetailRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Here we are for sure, context object holds a valid and decoded JWT token.
        SecurityContext context = SecurityContextHolder.getContext();
        try {
            if (context.getAuthentication() instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken auth = (JwtAuthenticationToken) context.getAuthentication();
                String cognitoSub = auth.getName();
                CognitoUserDetail user = userRepository.findById(cognitoSub).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
                request.setAttribute(CURRENT_USER_ATTRIBUTE, user);
            }
        } catch (UsernameNotFoundException ex) {
            log.error("Encountered error while finding user with current authentication token", ex);
            sendUnAuthorized(response, ex);
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendUnAuthorized(HttpServletResponse response, UsernameNotFoundException ex) {
        response.setStatus(HttpStatus.FORBIDDEN.value());

        if (ex != null) {
            try (Writer writer = response.getWriter()) {
                writer.write(ex.getMessage());
            } catch (IOException ioEx) {
                throw new RuntimeException(ioEx);
            }
        }
    }
}

package com.tic.tac.tictactoeback.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game_sessions")
public class GameSession {

    public enum GameResult {
        Unfinished,
        PlayerOneWon,
        PlayerTwoWon,
        Draw
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_one_id", referencedColumnName = "id")
    private UserDetails player1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_two_id", referencedColumnName = "id")
    private UserDetails player2;

    @Column(nullable = false)
    private char playerOneShape;

    @Column(nullable = false)
    private char playerTwoShape;
    
    @Column(nullable = false)
    private GameResult gameResult;

}

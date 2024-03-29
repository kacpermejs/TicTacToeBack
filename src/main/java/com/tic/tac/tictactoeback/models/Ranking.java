package com.tic.tac.tictactoeback.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ranking")
public class Ranking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private int matchesWon;

    @NotNull
    @Column(nullable = false)
    private int matchesLost;

    @NotNull
    @Column(nullable = false)
    private int score;

    //TODO: some better calculation
    public void updateScoreWin(int opponentScore) {
        if (opponentScore <= this.score) {
            this.score++;
        }
    }

    public void updateScoreLost(int opponentScore) {
        if (opponentScore <= this.score && this.score > 0 ) {
            this.score--;
        } 
    }

    public void updateScoreDraw(int opponentScore) {
        
    }

    public void matchWon() {
        this.matchesWon++;
    }


    public void matchLost() {
        this.matchesLost++;
    }
}

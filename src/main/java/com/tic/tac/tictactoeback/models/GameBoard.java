package com.tic.tac.tictactoeback.models;

import java.util.Arrays;

public class GameBoard {

    private char currentPlayer;
    private boolean gameWon;
    private boolean gameEnded;
    private char winner;

    private char[][] board;

    public GameBoard() {
        currentPlayer = 'O'; // Start with player 'O'
        board = new char[3][3];
        for (char[] row : board) {
            Arrays.fill(row, ' '); // Initialize the board with empty spaces
        }
        this.gameWon = false;
        this.gameEnded = false;
        winner = ' '; // No winner initially
    }
    
    public boolean makeMove(int row, int col) {
        if (isValidMove(row, col)) {
            board[row][col] = currentPlayer;
            checkForWin(row, col);
            switchPlayer();
            return true;
        }
        return false;
    }

    public boolean gameEnded() {
        return gameEnded;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public char getWinner() {
        return winner;
    }

    private boolean isValidMove(int row, int col) {
        return !gameWon && !gameEnded && board[row][col] == ' ';
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void checkForWin(int row, int col) {
        // Check for a win in the current row, column, and diagonals
        if (checkRow(row) || checkColumn(col) || checkDiagonals(row, col)) {
            gameWon = true;
            gameEnded = true;
            winner = currentPlayer;
        }

        if (boardFull()) {
            gameEnded = true;
        }
    }

    private boolean boardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false; // Found an empty cell, board is not full
                }
            }
        }
        return true; // All cells are filled, board is full
    }

    private boolean checkRow(int row) {
        return board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer;
    }

    private boolean checkColumn(int col) {
        return board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer;
    }

    private boolean checkDiagonals(int row, int col) {
        return (row == col && checkMainDiagonal()) || (row + col == 2 && checkAntiDiagonal());
    }

    private boolean checkMainDiagonal() {
        return board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer;
    }

    private boolean checkAntiDiagonal() {
        return board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer;
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
}

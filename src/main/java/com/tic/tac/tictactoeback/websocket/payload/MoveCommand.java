package com.tic.tac.tictactoeback.websocket.payload;

public record MoveCommand( Long sessionId, Long playerId, int row, int column) {}

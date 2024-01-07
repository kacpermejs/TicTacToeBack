package com.tic.tac.tictactoeback.websocket.payload;

public record MoveCommand( Long sessionId, String playerId, int row, int column) {}

package com.telegame.code.models.games.kingolaser.pieces;

public interface Movable {
    boolean rotate(String rotateTo, Piece piece);
    boolean move(int nextY, int nextX);
}

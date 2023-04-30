package com.telegame.code.models.kingolaser.pieces;

public interface Movable {
    boolean rotate(String rotateTo);
    boolean move(int nextY, int nextX);
}

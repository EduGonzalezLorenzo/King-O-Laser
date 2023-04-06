package com.telegame.code.models.kingolaser.pieces;

public interface Movable {
    void rotate(String rotateTo);
    boolean move(int[] nextPosition);
}

package com.kingolaser.code.models;

public interface Movable {
    void rotate(String rotateTo);
    boolean move(int[] nextPosition);
}

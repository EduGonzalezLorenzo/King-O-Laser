package com.telegame.code.models.kingolaser.pieces;

import jakarta.persistence.Entity;

@Entity
public class Laser extends Piece {

    @Override
    public boolean move(int nextY, int nextX) {
        return false;
    }

    @Override
    public boolean rotate(String rotateTo) {
        return false;
    }

}

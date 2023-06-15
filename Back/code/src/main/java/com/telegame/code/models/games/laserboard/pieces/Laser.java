package com.telegame.code.models.games.laserboard.pieces;

import jakarta.persistence.Entity;

@Entity
public class Laser extends Piece {

    @Override
    public boolean move(int nextY, int nextX) {
        return false;
    }

    @Override
    public boolean rotate(String rotateTo, Piece piece) {
        switch (piece.getOwner()) {
            case PLAYER_ONE -> {
                if (this.getRotation() == Direction.NORTH) {
                    this.rotation = Direction.WEST;
                    return true;
                }
                if (this.getRotation() == Direction.WEST) {
                    this.rotation = Direction.NORTH;
                    return true;
                }
            }
            case PLAYER_TWO -> {
                if (this.getRotation() == Direction.SOUTH) {
                    this.rotation = Direction.EAST;
                    return true;
                }
                if (this.getRotation() == Direction.EAST) {
                    this.rotation = Direction.SOUTH;
                    return true;
                }
            }
        }
        return false;
    }
}

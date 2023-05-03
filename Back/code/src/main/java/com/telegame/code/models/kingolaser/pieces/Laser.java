package com.telegame.code.models.kingolaser.pieces;

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
                if(piece.getRotation() == Direction.NORTH) piece.setRotation(Direction.WEST);
                if(piece.getRotation() == Direction.WEST) piece.setRotation(Direction.NORTH);
                return true;
            }
            case PLAYER_TWO -> {
                if(piece.getRotation() == Direction.SOUTH) piece.setRotation(Direction.EAST);
                if(piece.getRotation() == Direction.EAST) piece.setRotation(Direction.SOUTH);
                return true;
            }
        }
        return false;
    }

}

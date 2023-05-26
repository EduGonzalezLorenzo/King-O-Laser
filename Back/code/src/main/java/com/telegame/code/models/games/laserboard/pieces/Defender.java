package com.telegame.code.models.games.laserboard.pieces;

import com.telegame.code.models.games.laserboard.Block;
import com.telegame.code.models.games.laserboard.Hit;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@Entity
public class Defender extends Piece {

    public Defender(Direction rotation) {
        switch (rotation) {
            case NORTH -> {
                this.addSide(Piece.Direction.NORTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Hit());
                this.addSide(Piece.Direction.SOUTH, new Block());
                this.addSide(Piece.Direction.WEST, new Hit());
            }
            case EAST -> {
                this.addSide(Piece.Direction.NORTH, new Hit());
                this.addSide(Piece.Direction.SOUTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Hit());
                this.addSide(Piece.Direction.WEST, new Block());
            }
            case SOUTH -> {
                this.addSide(Piece.Direction.NORTH, new Block());
                this.addSide(Piece.Direction.SOUTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Hit());
                this.addSide(Piece.Direction.WEST, new Hit());
            }
            case WEST -> {
                this.addSide(Piece.Direction.NORTH, new Hit());
                this.addSide(Piece.Direction.SOUTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Block());
                this.addSide(Piece.Direction.WEST, new Hit());
            }
        }
    }

    public static void rotate(Direction rotation, Piece piece) {
        piece.setSides(new HashMap<>());
        switch (rotation) {
            case NORTH -> {
                piece.addSide(Piece.Direction.NORTH, new Hit());
                piece.addSide(Piece.Direction.EAST, new Hit());
                piece.addSide(Piece.Direction.SOUTH, new Block());
                piece.addSide(Piece.Direction.WEST, new Hit());
            }
            case EAST -> {
                piece.addSide(Piece.Direction.NORTH, new Hit());
                piece.addSide(Piece.Direction.SOUTH, new Hit());
                piece.addSide(Piece.Direction.EAST, new Hit());
                piece.addSide(Piece.Direction.WEST, new Block());
            }
            case SOUTH -> {
                piece.addSide(Piece.Direction.NORTH, new Block());
                piece.addSide(Piece.Direction.SOUTH, new Hit());
                piece.addSide(Piece.Direction.EAST, new Hit());
                piece.addSide(Piece.Direction.WEST, new Hit());
            }
            case WEST -> {
                piece.addSide(Piece.Direction.NORTH, new Hit());
                piece.addSide(Piece.Direction.SOUTH, new Hit());
                piece.addSide(Piece.Direction.EAST, new Block());
                piece.addSide(Piece.Direction.WEST, new Hit());
            }
        }
    }
}

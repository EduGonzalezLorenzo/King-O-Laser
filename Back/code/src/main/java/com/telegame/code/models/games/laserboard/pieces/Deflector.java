package com.telegame.code.models.games.laserboard.pieces;

import com.telegame.code.models.games.laserboard.Deflect;
import com.telegame.code.models.games.laserboard.Hit;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@Entity
public class Deflector extends Piece {
    public Deflector(Direction rotation) {
        switch (rotation) {
            case NORTH -> {
                this.addSide(Piece.Direction.NORTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Hit());
                this.addSide(Piece.Direction.SOUTH, new Deflect());
                this.addSide(Piece.Direction.WEST, new Deflect());
            }
            case EAST -> {
                this.addSide(Piece.Direction.NORTH, new Deflect());
                this.addSide(Piece.Direction.SOUTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Hit());
                this.addSide(Piece.Direction.WEST, new Deflect());
            }
            case SOUTH -> {
                this.addSide(Piece.Direction.NORTH, new Deflect());
                this.addSide(Piece.Direction.SOUTH, new Hit());
                this.addSide(Piece.Direction.EAST, new Deflect());
                this.addSide(Piece.Direction.WEST, new Hit());
            }
            case WEST -> {
                this.addSide(Piece.Direction.NORTH, new Hit());
                this.addSide(Piece.Direction.SOUTH, new Deflect());
                this.addSide(Piece.Direction.EAST, new Deflect());
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
                piece.addSide(Piece.Direction.SOUTH, new Deflect());
                piece.addSide(Piece.Direction.WEST, new Deflect());
            }
            case EAST -> {
                piece.addSide(Piece.Direction.NORTH, new Deflect());
                piece.addSide(Piece.Direction.SOUTH, new Hit());
                piece.addSide(Piece.Direction.EAST, new Hit());
                piece.addSide(Piece.Direction.WEST, new Deflect());
            }
            case SOUTH -> {
                piece.addSide(Piece.Direction.NORTH, new Deflect());
                piece.addSide(Piece.Direction.SOUTH, new Hit());
                piece.addSide(Piece.Direction.EAST, new Deflect());
                piece.addSide(Piece.Direction.WEST, new Hit());
            }
            case WEST -> {
                piece.addSide(Piece.Direction.NORTH, new Hit());
                piece.addSide(Piece.Direction.SOUTH, new Deflect());
                piece.addSide(Piece.Direction.EAST, new Deflect());
                piece.addSide(Piece.Direction.WEST, new Hit());
            }
        }
    }
}

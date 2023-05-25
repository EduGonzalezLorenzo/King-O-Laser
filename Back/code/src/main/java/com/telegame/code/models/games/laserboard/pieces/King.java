package com.telegame.code.models.games.laserboard.pieces;

import com.telegame.code.models.games.laserboard.Hit;
import jakarta.persistence.Entity;

@Entity
public class King extends Piece {
    public King() {
        this.addSide(Piece.Direction.NORTH, new Hit());
        this.addSide(Piece.Direction.NORTH, new Hit());
        this.addSide(Piece.Direction.NORTH, new Hit());
        this.addSide(Piece.Direction.NORTH, new Hit());
    }
}

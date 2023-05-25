package com.telegame.code.models.games.laserboard.pieces;

import com.telegame.code.models.games.laserboard.Deflect;
import jakarta.persistence.Entity;

@Entity
public class Bouncer extends Piece {
    public Bouncer() {
        this.addSide(Piece.Direction.NORTH, new Deflect());
        this.addSide(Piece.Direction.EAST, new Deflect());
        this.addSide(Piece.Direction.SOUTH, new Deflect());
        this.addSide(Piece.Direction.WEST, new Deflect());
    }
}

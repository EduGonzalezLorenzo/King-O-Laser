package com.telegame.code.models.games.laserboard;

import com.telegame.code.models.games.laserboard.pieces.Piece;
import com.telegame.code.models.games.laserboard.pieces.PieceSide;


public class Hit implements PieceSide {
    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {
        return Piece.Direction.HIT;
    }
}

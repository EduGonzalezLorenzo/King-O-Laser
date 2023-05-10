package com.telegame.code.models.games.kingolaser;

import com.telegame.code.models.games.kingolaser.pieces.Piece;
import com.telegame.code.models.games.kingolaser.pieces.PieceSide;


public class Hit implements PieceSide {
    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {
        return Piece.Direction.HIT;
    }
}

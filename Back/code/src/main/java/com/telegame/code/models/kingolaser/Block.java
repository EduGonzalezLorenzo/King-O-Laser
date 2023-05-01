package com.telegame.code.models.kingolaser;

import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.models.kingolaser.pieces.PieceSide;

public class Block implements PieceSide {
    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {
        System.out.println("Bloquear láser");
        return Piece.Direction.STOPPED;
    }
}

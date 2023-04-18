package com.telegame.code.models.kingolaser.pieces;

public interface PieceSide {

    Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer);
}

package com.telegame.code.models.games.kingolaser.pieces;


public interface PieceSide {

    Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer);
}

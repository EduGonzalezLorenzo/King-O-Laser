package com.telegame.code.models.games.laserboard.pieces;


public interface PieceSide {

    Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer);
}

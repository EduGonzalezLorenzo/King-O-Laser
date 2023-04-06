package com.kingolaser.code.models;

public interface PieceSide {

    Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer);
}

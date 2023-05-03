package com.telegame.code.models.kingolaser.pieces;

import com.telegame.code.models.kingolaser.pieces.Piece;

import java.util.Map;


public interface PieceSide {

    Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer);
}

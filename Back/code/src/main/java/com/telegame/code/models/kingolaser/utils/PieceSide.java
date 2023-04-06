package com.telegame.code.models.kingolaser.utils;

import com.telegame.code.models.kingolaser.pieces.Piece;

public interface PieceSide {

    Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer);
}

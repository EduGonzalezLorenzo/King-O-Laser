package com.telegame.code.models.kingolaser.pieces;

public class Block implements PieceSide {
    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {
        System.out.println("Bloquear láser");
        return null;
    }
}

package com.kingolaser.code.models;

public class Hit  implements PieceSide{
    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {
        System.out.println("La pieza ha muerto");
        return null;
    }
}

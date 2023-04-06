package com.kingolaser.code.models;

public class Deflect implements PieceSide{
    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {

        if(bouncer) {
            if(direction == Piece.Direction.NORTH && rotation == Piece.Direction.NORTH) return Piece.Direction.WEST;
            if(direction == Piece.Direction.NORTH && rotation == Piece.Direction.EAST) return Piece.Direction.EAST;

//            if(direction == 2 && rotation == 1) return 3;
//            if(direction == 2 && rotation == 2) return 1;
//
//            if(direction == 3 && rotation == 1) return 2;
//            if(direction == 3 && rotation == 2) return 4;
//
//            if(direction == 4 && rotation == 1) return 1;
//            if(direction == 4 && rotation == 2) return 3;
        }

        if(direction == Piece.Direction.NORTH && rotation == Piece.Direction.NORTH) return Piece.Direction.WEST;
        if(direction == Piece.Direction.NORTH && rotation == Piece.Direction.WEST) return Piece.Direction.EAST;

        if(direction == Piece.Direction.EAST && rotation == Piece.Direction.NORTH) return Piece.Direction.SOUTH;
        if(direction == Piece.Direction.EAST && rotation == Piece.Direction.EAST) return Piece.Direction.NORTH;

        if(direction == Piece.Direction.SOUTH && rotation == Piece.Direction.EAST) return Piece.Direction.WEST;
        if(direction == Piece.Direction.SOUTH && rotation == Piece.Direction.SOUTH) return Piece.Direction.EAST;

        if(direction == Piece.Direction.WEST && rotation == Piece.Direction.SOUTH) return Piece.Direction.NORTH;
        if(direction == Piece.Direction.WEST && rotation == Piece.Direction.WEST) return Piece.Direction.SOUTH;

        return Piece.Direction.NORTH;
    }
}

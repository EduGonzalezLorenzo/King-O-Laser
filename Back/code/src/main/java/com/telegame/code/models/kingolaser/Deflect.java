package com.telegame.code.models.kingolaser;

import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.models.kingolaser.pieces.PieceSide;

public class Deflect implements PieceSide {
    @Override
    public Piece.Direction interact(Piece.Direction laserDirection, Piece.Direction rotation, boolean bouncer) {

        if(bouncer) {
            if(laserDirection == Piece.Direction.NORTH && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH)) return Piece.Direction.WEST;
            if(laserDirection == Piece.Direction.NORTH && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) return Piece.Direction.EAST;

            if(laserDirection == Piece.Direction.EAST && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH)) return Piece.Direction.SOUTH;
            if(laserDirection == Piece.Direction.EAST && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) return Piece.Direction.NORTH;

            if(laserDirection == Piece.Direction.SOUTH && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH)) return Piece.Direction.EAST;
            if(laserDirection == Piece.Direction.SOUTH && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) return Piece.Direction.WEST;

            if(laserDirection == Piece.Direction.WEST && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH)) return Piece.Direction.NORTH;
            if(laserDirection == Piece.Direction.WEST && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) return Piece.Direction.SOUTH;

        }

        if(laserDirection == Piece.Direction.NORTH && rotation == Piece.Direction.NORTH) return Piece.Direction.WEST;
        if(laserDirection == Piece.Direction.NORTH && rotation == Piece.Direction.WEST) return Piece.Direction.EAST;

        if(laserDirection == Piece.Direction.EAST && rotation == Piece.Direction.NORTH) return Piece.Direction.SOUTH;
        if(laserDirection == Piece.Direction.EAST && rotation == Piece.Direction.EAST) return Piece.Direction.NORTH;

        if(laserDirection == Piece.Direction.SOUTH && rotation == Piece.Direction.EAST) return Piece.Direction.WEST;
        if(laserDirection == Piece.Direction.SOUTH && rotation == Piece.Direction.SOUTH) return Piece.Direction.EAST;

        if(laserDirection == Piece.Direction.WEST && rotation == Piece.Direction.SOUTH) return Piece.Direction.NORTH;
        if(laserDirection == Piece.Direction.WEST && rotation == Piece.Direction.WEST) return Piece.Direction.SOUTH;

        return Piece.Direction.NORTH;
    }
}

package com.telegame.code.models.games.laserboard;

import com.telegame.code.models.games.laserboard.pieces.Piece;
import com.telegame.code.models.games.laserboard.pieces.PieceSide;

public class Deflect implements PieceSide {
    @Override
    public Piece.Direction interact(Piece.Direction laserDirection, Piece.Direction rotation, boolean bouncer) {
        if (bouncer) {
            if (bouncerDirectionNorth(laserDirection, rotation)) return Piece.Direction.NORTH;
            if (bouncerDirectionSouth(laserDirection, rotation)) return Piece.Direction.SOUTH;
            if (bouncerDirectionEast(laserDirection, rotation)) return Piece.Direction.EAST;
            if (bouncerDirectionWest(laserDirection, rotation)) return Piece.Direction.WEST;
        } else {
            if (noBouncerDirectionNorth(laserDirection, rotation)) return Piece.Direction.NORTH;
            if (noBouncerDirectionSouth(laserDirection, rotation)) return Piece.Direction.SOUTH;
            if (noBouncerDirectionEast(laserDirection, rotation)) return Piece.Direction.EAST;
            if (noBouncerDirectionWest(laserDirection, rotation)) return Piece.Direction.WEST;
        }
        return Piece.Direction.NORTH;
    }


    private boolean bouncerDirectionNorth(Piece.Direction laserDirection, Piece.Direction rotation) {
        return (laserDirection == Piece.Direction.EAST && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) ||
                (laserDirection == Piece.Direction.WEST && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH));
    }

    private boolean bouncerDirectionSouth(Piece.Direction laserDirection, Piece.Direction rotation) {
        return (laserDirection == Piece.Direction.WEST && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) ||
                (laserDirection == Piece.Direction.EAST && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH));
    }

    private boolean bouncerDirectionEast(Piece.Direction laserDirection, Piece.Direction rotation) {
        return (laserDirection == Piece.Direction.NORTH && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) ||
                (laserDirection == Piece.Direction.SOUTH && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH));
    }

    private boolean bouncerDirectionWest(Piece.Direction laserDirection, Piece.Direction rotation) {
        return (laserDirection == Piece.Direction.SOUTH && (rotation == Piece.Direction.EAST || rotation == Piece.Direction.WEST)) ||
                (laserDirection == Piece.Direction.NORTH && (rotation == Piece.Direction.NORTH || rotation == Piece.Direction.SOUTH));
    }

    private boolean noBouncerDirectionNorth(Piece.Direction laserDirection, Piece.Direction rotation) {
        return ((laserDirection == Piece.Direction.WEST && rotation == Piece.Direction.SOUTH)) ||
                ((laserDirection == Piece.Direction.EAST && rotation == Piece.Direction.EAST));
    }

    private boolean noBouncerDirectionSouth(Piece.Direction laserDirection, Piece.Direction rotation) {
        return ((laserDirection == Piece.Direction.EAST && rotation == Piece.Direction.NORTH)) ||
                ((laserDirection == Piece.Direction.WEST && rotation == Piece.Direction.WEST));
    }

    private boolean noBouncerDirectionEast(Piece.Direction laserDirection, Piece.Direction rotation) {
        return ((laserDirection == Piece.Direction.NORTH && rotation == Piece.Direction.WEST)) ||
                ((laserDirection == Piece.Direction.SOUTH && rotation == Piece.Direction.SOUTH));
    }

    private boolean noBouncerDirectionWest(Piece.Direction laserDirection, Piece.Direction rotation) {
        return ((laserDirection == Piece.Direction.SOUTH && rotation == Piece.Direction.EAST)) ||
                ((laserDirection == Piece.Direction.NORTH && rotation == Piece.Direction.NORTH));
    }
}

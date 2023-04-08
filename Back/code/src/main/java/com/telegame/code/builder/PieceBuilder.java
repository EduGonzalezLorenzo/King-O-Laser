package com.telegame.code.builder;

import com.telegame.code.models.Player;
import com.telegame.code.models.kingolaser.Block;
import com.telegame.code.models.kingolaser.pieces.*;
import com.telegame.code.models.kingolaser.Deflect;
import com.telegame.code.models.kingolaser.Hit;

import java.util.HashMap;
import java.util.Map;

public class PieceBuilder {

    public static Piece buildPiece(Player owner, String type, int posY, int posX, Piece.Direction rotation) {
        Piece piece = new King();
        switch (type) {
            case "king" -> {
                Piece king = new King();
                king.setOwner(owner);
                king.setPosY(posY);
                king.setPosX(posX);
                king.setRotation(rotation);
                king.setSide(Piece.Direction.NORTH, new Hit());
                king.setSide(Piece.Direction.SOUTH, new Hit());
                king.setSide(Piece.Direction.EAST, new Hit());
                king.setSide(Piece.Direction.WEST, new Hit());
                return king;
            }
            case "laser" -> {
                Piece laser = new Laser();
                laser.setOwner(owner);
                laser.setPosY(posY);
                laser.setPosX(posX);
                laser.setRotation(rotation);
                laser.setSide(Piece.Direction.NORTH, new Block());
                laser.setSide(Piece.Direction.SOUTH, new Block());
                laser.setSide(Piece.Direction.EAST, new Block());
                laser.setSide(Piece.Direction.WEST, new Block());
                return laser;
            }
            case "deflector" -> {
                Piece deflector = new Deflector();
                deflector.setOwner(owner);
                deflector.setPosY(posY);
                deflector.setPosX(posX);
                deflector.setRotation(rotation);
                deflector.setSides(buildDeflectorSides(deflector.getRotation()));
                return deflector;
            }
            case "defender" -> {
                Piece defender = new Defender();
                defender.setOwner(owner);
                defender.setPosY(posY);
                defender.setPosX(posX);
                defender.setRotation(rotation);
                defender.setSides(buildDefenderSides(defender.getRotation()));
                return defender;
            }
            case "bouncer" -> {
                Piece bouncer = new Bouncer();
                bouncer.setOwner(owner);
                bouncer.setPosY(posY);
                bouncer.setPosX(posX);
                bouncer.setRotation(rotation);
                bouncer.setSide(Piece.Direction.NORTH, new Deflect());
                bouncer.setSide(Piece.Direction.EAST, new Deflect());
                bouncer.setSide(Piece.Direction.SOUTH, new Deflect());
                bouncer.setSide(Piece.Direction.WEST, new Deflect());
                return bouncer;
            }
        }
        return piece;
    }

    private static Map<Piece.Direction, PieceSide> buildDefenderSides(Piece.Direction rotation) {
        Map<Piece.Direction, PieceSide> sides = new HashMap<>();
        switch (rotation) {
            case NORTH -> {
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.SOUTH, new Block());
                sides.put(Piece.Direction.WEST, new Hit());
            }
            case EAST -> {
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.WEST, new Block());
            }
            case SOUTH -> {
                sides.put(Piece.Direction.NORTH, new Block());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.WEST, new Hit());
            }
            case WEST -> {
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Block());
                sides.put(Piece.Direction.WEST, new Hit());
            }
        }
        return sides;
    }

    public static Map<Piece.Direction, PieceSide> buildDeflectorSides(Piece.Direction rotation) {
        Map<Piece.Direction, PieceSide> sides = new HashMap<>();
        switch (rotation) {
            case NORTH -> {
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.SOUTH, new Deflect());
                sides.put(Piece.Direction.WEST, new Deflect());
            }
            case EAST -> {
                sides.put(Piece.Direction.NORTH, new Deflect());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.WEST, new Deflect());
            }
            case SOUTH -> {
                sides.put(Piece.Direction.NORTH, new Deflect());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Deflect());
                sides.put(Piece.Direction.WEST, new Hit());
            }
            case WEST -> {
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.SOUTH, new Deflect());
                sides.put(Piece.Direction.EAST, new Deflect());
                sides.put(Piece.Direction.WEST, new Hit());
            }
        }
        return sides;
    }

}

package com.telegame.code.builder;

import com.telegame.code.models.kingolaser.pieces.PieceSide;
import com.telegame.code.models.kingolaser.pieces.Block;
import com.telegame.code.models.kingolaser.Deflect;
import com.telegame.code.models.kingolaser.Hit;
import com.telegame.code.models.kingolaser.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class PieceBuilder {

    // values[0] = player
    // values[1] = posY
    // values[2] = posX
    // values[3] = rotation;

//    public static Piece buildKing(int player_id, int posY, int posX,
//                                  Piece.Direction rotation) {
//        Piece king = new Piece();
//
//        king.setPlayer(player_id);
//        king.setType(Piece.Type.KING);
//        king.setPosY(posY);
//        king.setPosX(posX);
//        king.setRotation(rotation);
//
//        king.setSide(Piece.Direction.NORTH, new Hit());
//        king.setSide(Piece.Direction.SOUTH, new Hit());
//        king.setSide(Piece.Direction.EAST, new Hit());
//        king.setSide(Piece.Direction.WEST, new Hit());
//
//        return king;
//    }
//
//    public static Piece buildBlock(int player_id, int posY, int posX,
//                                   Piece.Direction rotation) {
//        Piece block = new Piece();
//
//        block.setPlayer(player_id);
//        block.setType(Piece.Type.BLOCK);
//        block.setPosY(posY);
//        block.setPosX(posX);
//        block.setRotation(rotation);
//
//        block.setSide(Piece.Direction.NORTH, new Hit());
//        block.setSide(Piece.Direction.SOUTH, new Block());
//        block.setSide(Piece.Direction.EAST, new Hit());
//        block.setSide(Piece.Direction.WEST, new Hit());
//
//        return block;
//    }
//
//    public static Piece buildLaser(int player_id, int posY, int posX,
//                                   Piece.Direction rotation) {
//        Piece laser = new Piece();
//
//        laser.setPlayer(player_id);
//        laser.setType(Piece.Type.LASER);
//        laser.setPosY(posY);
//        laser.setPosX(posX);
//        laser.setRotation(rotation);
//
//        laser.setSide(Piece.Direction.NORTH, new Block());
//        laser.setSide(Piece.Direction.SOUTH, new Block());
//        laser.setSide(Piece.Direction.EAST, new Block());
//        laser.setSide(Piece.Direction.WEST, new Block());
//
//        return laser;
//    }
//
//    public static Piece buildDeflector(int player_id, int posY, int posX,
//                                       Piece.Direction rotation) {
//        Piece deflector = new Piece();
//
//        deflector.setPlayer(player_id);
//        deflector.setType(Piece.Type.DEFLECTOR);
//        deflector.setPosY(posY);
//        deflector.setPosX(posX);
//        deflector.setRotation(rotation);
//
//        deflector.setSides(buildDeflectorSides(deflector.getRotation()));
//
//        return deflector;
//    }

    public static Map<Piece.Direction, PieceSide> buildDeflectorSides(Piece.Direction rotation) {
        Map<Piece.Direction, PieceSide> sides = new HashMap<>();
        switch (rotation) {
            case NORTH:
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.SOUTH, new Deflect());
                sides.put(Piece.Direction.WEST, new Deflect());
                break;
            case EAST:
                sides.put(Piece.Direction.NORTH, new Deflect());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Hit());
                sides.put(Piece.Direction.WEST, new Deflect());
                break;
            case SOUTH:
                sides.put(Piece.Direction.NORTH, new Deflect());
                sides.put(Piece.Direction.SOUTH, new Hit());
                sides.put(Piece.Direction.EAST, new Deflect());
                sides.put(Piece.Direction.WEST, new Hit());
                break;
            case WEST:
                sides.put(Piece.Direction.NORTH, new Hit());
                sides.put(Piece.Direction.SOUTH, new Deflect());
                sides.put(Piece.Direction.EAST, new Deflect());
                sides.put(Piece.Direction.WEST, new Hit());
                break;
        }
        return sides;
    }

//    public static Piece buildBouncer(int player_id, int posY, int posX,
//                                    Piece.Direction rotation) {
//        Piece bouncer = new Piece();
//
//        bouncer.setPlayer(player_id);
//        bouncer.setType(Piece.Type.KING);
//        bouncer.setPosY(posY);
//        bouncer.setPosX(posX);
//        bouncer.setRotation(rotation);
//
//        bouncer.setSide(Piece.Direction.NORTH, new Deflect());
//        bouncer.setSide(Piece.Direction.EAST, new Deflect());
//        bouncer.setSide(Piece.Direction.SOUTH, new Deflect());
//        bouncer.setSide(Piece.Direction.WEST, new Deflect());
//
//        return bouncer;
//    }
}

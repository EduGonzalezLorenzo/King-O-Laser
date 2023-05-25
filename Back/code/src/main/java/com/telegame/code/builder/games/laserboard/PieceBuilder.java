package com.telegame.code.builder.games.laserboard;

import com.telegame.code.models.games.laserboard.pieces.*;

public class PieceBuilder {
    public static Piece buildPiece(String type, Piece.Owner owner, int posY, int posX, Piece.Direction rotation) {
        Piece piece = new King();
        System.out.println("type: " + type);
        switch (type) {
            case "King" -> {
                Piece king = new King();
                king.setOwner(owner);
                king.setPosY(posY);
                king.setPosX(posX);
                king.setRotation(rotation);
                return king;
            }
            case "Laser" -> {
                Piece laser = new Laser();
                laser.setOwner(owner);
                laser.setPosY(posY);
                laser.setPosX(posX);
                laser.setRotation(rotation);
                return laser;
            }
            case "Deflector" -> {
                Piece deflector = new Deflector(rotation);
                deflector.setOwner(owner);
                deflector.setPosY(posY);
                deflector.setPosX(posX);
                deflector.setRotation(rotation);
                return deflector;
            }
            case "Defender" -> {
                Piece defender = new Defender(rotation);
                defender.setOwner(owner);
                defender.setPosY(posY);
                defender.setPosX(posX);
                defender.setRotation(rotation);
                return defender;
            }
            case "Bouncer" -> {
                Piece bouncer = new Bouncer();
                bouncer.setOwner(owner);
                bouncer.setPosY(posY);
                bouncer.setPosX(posX);
                bouncer.setRotation(rotation);
                return bouncer;
            }
        }
        return piece;
    }

}

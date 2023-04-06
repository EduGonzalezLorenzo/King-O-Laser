package com.kingolaser.code.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Piece implements Movable {

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    public enum Type {
        KING, BLOCK, DEFLECTOR, BOUNCER, LASER
    }

    private int player_id;
    private Type type;
    private int posX;
    private int posY;
    private Direction rotation;
    private Map<Direction, PieceSide> sides = new HashMap<>();


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Direction getRotation() {
        return rotation;
    }

    public void setRotation(Direction rotation) {
        this.rotation = rotation;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer(int player_id) {
        this.player_id = player_id;
    }

    public Map<Direction, PieceSide> getSides() {
        return sides;
    }

    public void setSides(Map<Direction, PieceSide> sides) {
        this.sides = sides;
    }

    public void setSide(Direction side, PieceSide pieceSide) {
        this.sides.put(side, pieceSide);
    }

    public PieceSide getSide(Direction side) {
        return this.sides.get(side);
    }

    @Override
    public void rotate(String rotateTo) {
        if(Objects.equals(rotateTo, "R")) {
            if(this.rotation == Direction.NORTH) this.rotation = Direction.EAST;
            if(this.rotation == Direction.EAST) this.rotation = Direction.SOUTH;
            if(this.rotation == Direction.SOUTH) this.rotation = Direction.WEST;
            if(this.rotation == Direction.WEST) this.rotation = Direction.NORTH;
        }
        if(Objects.equals(rotateTo, "L")) {
            if(this.rotation == Direction.NORTH) this.rotation = Direction.WEST;
            if(this.rotation == Direction.EAST) this.rotation = Direction.NORTH;
            if(this.rotation == Direction.SOUTH) this.rotation = Direction.EAST;
            if(this.rotation == Direction.WEST) this.rotation = Direction.SOUTH;
        }
        this.setSides(PieceBuilder.buildDeflectorSides(this.rotation));
    }

    @Override
    public boolean move(int[] nextPosition) {
        int nextY = nextPosition[0];
        int nextX = nextPosition[1];

        boolean validateY = (nextY == this.posY || nextY == this.posY +1 || nextY == this.posY -1);
        boolean validateX = (nextX == this.posX || nextX == this.posX +1 || nextX == this.posX -1);

        if(validateY && validateX) {
            this.posY = nextY;
            this.posX = nextX;
            return true;
        } else {
            System.out.println("Movimiento incorrecto");
            return false;
        }
    }
}

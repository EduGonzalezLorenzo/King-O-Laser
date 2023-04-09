package com.telegame.code.models.kingolaser.pieces;

import com.telegame.code.builder.PieceBuilder;
import com.telegame.code.models.Player;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Piece implements Movable {

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private Player owner;
    private int posX;
    private int posY;
    private Direction rotation;
    private Map<Direction, PieceSide> sides = new HashMap<>();

    public void setSide(Direction side, PieceSide pieceSide) {
        this.sides.put(side, pieceSide);
    }

    public PieceSide getSide(Direction side) {
        return this.sides.get(side);
    }


    @Override
    public void rotate(String rotateTo) {
        if(Objects.equals(rotateTo, "R")) {
            switch(this.rotation) {
                case NORTH -> this.rotation = Direction.EAST;
                case EAST -> this.rotation = Direction.SOUTH;
                case SOUTH -> this.rotation = Direction.WEST;
                case WEST -> this.rotation = Direction.NORTH;
            }
        }
        if(Objects.equals(rotateTo, "L")) {
            switch(this.rotation) {
                case NORTH -> this.rotation = Direction.WEST;
                case EAST -> this.rotation = Direction.NORTH;
                case SOUTH -> this.rotation = Direction.EAST;
                case WEST -> this.rotation = Direction.SOUTH;
            }
        }
        if(this.getClass() == Deflector.class) this.setSides(PieceBuilder.buildDeflectorSides(this.rotation));
        if(this.getClass() == Defender.class) this.setSides(PieceBuilder.buildDefenderSides(this.rotation));
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

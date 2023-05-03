package com.telegame.code.models.kingolaser;

import com.telegame.code.builder.PieceBuilder;
import com.telegame.code.models.Board;
import com.telegame.code.models.kingolaser.pieces.Bouncer;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.models.kingolaser.pieces.PieceSide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaserBeam {

    Piece.Direction direction;
    List<int[]> route;

//    public void setStep(int[] step) {
//        this.route.add(step);
//    }

    public Map<String, Object> shootLaser(Board.MatchStatus matchStatus, Piece.Direction direction, List<Piece> boardDisposition) {

        Map<String, Object> returnMap = new HashMap<>();

        int[] currentPosition;
        List<int[]> route = new ArrayList<>();

        if (matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) {
            currentPosition = new int[]{9, 7};
        } else {
            currentPosition = new int[]{0, 0};
        }

        Object[][] board = buildBoard(boardDisposition);


        while (currentPosition[0] >= 0 && currentPosition[0] <= 9 &&
                currentPosition[1] >= 0 && currentPosition[1] <= 7) {

            int[] newYX = forward(direction, currentPosition);
            int posY = newYX[0];
            int posX = newYX[1];


            if (board[posY][posX] instanceof Piece) {
                Piece piece = (Piece) board[posY][posX];
                System.out.println("sides: " + piece.getSides());
                PieceSide pieceSide = new Block();

                switch (direction) {
                    case NORTH:
                        pieceSide = piece.getSide(Piece.Direction.SOUTH);
                        break;
                    case EAST:
                        pieceSide = piece.getSide(Piece.Direction.WEST);
                        break;
                    case SOUTH:
                        pieceSide = piece.getSide(Piece.Direction.NORTH);
                        break;
                    case WEST:
                        pieceSide = piece.getSide(Piece.Direction.EAST);
                        break;
                }

                boolean bouncer = (piece instanceof Bouncer);

                Piece.Direction nextDirection = pieceSide.interact(direction, piece.getRotation(), bouncer);

                if (nextDirection == Piece.Direction.STOPPED) {
                    LaserBeam.drawBoard(board);
                    returnMap.put("message", "BLOCK");
                    route.add(newYX);
                    returnMap.put("route", route);
                    return returnMap;
                } else if (nextDirection == Piece.Direction.HIT) {
                    LaserBeam.drawBoard(board);
                    returnMap.put("message", "HIT");
                    route.add(forward(direction, currentPosition));
                    returnMap.put("route", route);
                    return returnMap;
                } else {
                    direction = nextDirection;
                    currentPosition = newYX;
                    route.add(newYX);
                }

            } else {
                board[posY][posX] = "  /\\  ";
                currentPosition[0] = posY;
                currentPosition[1] = posX;
                route.add(new int[]{posY, posX});
                if (posY == 0 && direction == Piece.Direction.NORTH || posY == 9 && direction == Piece.Direction.SOUTH) {
                    LaserBeam.drawBoard(board);
                    returnMap.put("message", "OUT");
                    returnMap.put("route", route);
                    return returnMap;
                }
                if (posX == 0 && direction == Piece.Direction.WEST || posX == 7 && direction == Piece.Direction.EAST) {
                    LaserBeam.drawBoard(board);
                    returnMap.put("message", "OUT");
                    returnMap.put("route", route);
                    return returnMap;
                }


            }
        }
//        LaserBeam.drawBoard(board);
//        returnMap.put("message", "END");
//        returnMap.put("route", route);
        return returnMap;
    }

    public int[] forward(Piece.Direction direction, int[] currentPosition) {
        int posY = currentPosition[0];
        int posX = currentPosition[1];
        switch (direction) {
            case NORTH:
                posY--;
                break;
            case EAST:
                posX++;
                break;
            case SOUTH:
                posY++;
                break;
            case WEST:
                posX--;
        }
        return new int[]{posY, posX};
    }

    public static Object[][] buildBoard(List<Piece> piecesList) {
        Object[][] board = new Object[10][8];
        for (Piece piece : piecesList) {
            piece = PieceBuilder.buildPiece(piece.getClass().toString(), piece.getOwner(), piece.getPosY(), piece.getPosX(), piece.getRotation());
            board[piece.getPosY()][piece.getPosX()] = piece;
        }
        return board;
    }

    public static void drawBoard(Object[][] board) {
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = "  " + i + ":" + j + " ";
                } else if (board[i][j] instanceof Piece) {
                    Piece piece = (Piece) board[i][j];
//                    board[i][j] = piece.getType().toString();
                    board[i][j] = piece.getRotation();
                }
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
            System.out.println("--------------------------------------------------------");
        }
    }

}

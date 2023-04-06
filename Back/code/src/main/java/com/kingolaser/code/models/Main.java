package com.kingolaser.code.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Piece> piecesList = new ArrayList<>();

        Piece deflector = PieceBuilder.buildDeflector(1, 5, 7, Piece.Direction.EAST);
        piecesList.add(deflector);

        Piece deflector2 = PieceBuilder.buildDeflector(1, 5, 3, Piece.Direction.WEST);
        piecesList.add(deflector2);

        Piece deflector3 = PieceBuilder.buildDeflector(1, 9, 3, Piece.Direction.SOUTH);
        piecesList.add(deflector3);

        Object[][] board = buildBoard(piecesList);

//        movePiece(board, new int[]{5,7}, new int[]{4,7}, null);
//
//        movePiece(board, new int[]{4,7}, new int[]{3,7}, null);

//        movePiece(board, new int[]{5,3}, new int[]{5,3}, "L");

        LaserBeam laserBeam = new LaserBeam();

        List<int[]> route = laserBeam.shootLaser(1, Piece.Direction.WEST, board);

        drawBoard(board);
        System.out.println(Arrays.deepToString(route.toArray()));

    }

    public static Object[][] buildBoard(List<Piece> piecesList) {

        Object[][] board = new Object[10][8];

        for(Piece piece : piecesList) {
            board[piece.getPosY()][piece.getPosX()] = piece;
        }
        return board;
    }

    public static void movePiece(Object[][] board, int[] currentPosition, int[] newPosition, String rotate) {
        int currentY = currentPosition[0];
        int currentX = currentPosition[1];

        int newY = newPosition[0];
        int newX = newPosition[1];

        Piece piece = (Piece) board[currentY][currentX];
        boolean validMove = piece.move(new int[]{newY, newX});
        if(rotate != null) {
            piece.rotate(rotate);
        } else if(validMove) {
            board[currentY][currentX] = null;
            board[newY][newX] = piece;
        }
    }

    public static void drawBoard(Object[][] board) {
        System.out.println();
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length ; j++) {
                if(board[i][j] == null) {
                    board[i][j] = "  " + i + ":" + j + " ";
                } else if(board[i][j] instanceof Piece) {
                    Piece piece = (Piece) board[i][j];
                    board[i][j] = piece.getType().toString();
                }
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
            System.out.println("--------------------------------------------------------");
        }
    }

}
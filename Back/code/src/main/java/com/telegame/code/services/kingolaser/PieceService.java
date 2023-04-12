package com.telegame.code.services.kingolaser;

import com.telegame.code.forms.MovementForm;
import com.telegame.code.models.Match;
import com.telegame.code.models.kingolaser.LaserBeam;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PieceService {

    public Match movePiece(Match match, MovementForm movementForm) {
        LaserBoard currentBoard =(LaserBoard) match.getBoard();
        List<Piece> piecesList = currentBoard.getPieceList();
        Object[][] board = new Object[10][8];

        for(Piece piece : piecesList) {
            board[piece.getPosY()][piece.getPosX()] = piece;
        }

        int currentY = movementForm.getCurrentPosY();
        int currentX = movementForm.getCurrentPosX();
        int newY = movementForm.getNewPosY();
        int newX = movementForm.getNewPosX();
        String rotate = movementForm.getRotateTo();

        Piece piece = (Piece) board[currentY][currentX];
        boolean validMove = piece.move(new int[]{newY, newX});
        if(rotate != null) {
            piece.rotate(rotate);
        } else if(validMove) {
            board[currentY][currentX] = null;
            board[newY][newX] = piece;
        }

        List<Piece> newPieceList = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] instanceof Piece) {
                    newPieceList.add((Piece) board[i][j]);
                }
            }
        }

        match.setBoard(new LaserBoard(newPieceList, new LaserBeam()));

        return match;
    }
}

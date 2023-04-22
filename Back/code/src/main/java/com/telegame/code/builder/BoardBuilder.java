package com.telegame.code.builder;

import com.telegame.code.models.kingolaser.pieces.Piece;


import java.util.ArrayList;
import java.util.List;

public class BoardBuilder {

    public static List<Piece> getBoardDisposition(String disposition) {
        List<Piece> piecesList = new ArrayList<>();
        if ("std".equals(disposition)) {
            Piece kingOne = PieceBuilder.buildPiece("king", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
            Piece kingTwo = PieceBuilder.buildPiece("king", Piece.Owner.PLAYER_TWO, 4, 4, Piece.Direction.NORTH);

            Piece defenderOne = PieceBuilder.buildPiece("defender", Piece.Owner.PLAYER_ONE, 5, 5, Piece.Direction.NORTH);
            Piece defenderTwo = PieceBuilder.buildPiece("defender", Piece.Owner.PLAYER_TWO, 6, 6, Piece.Direction.NORTH);

            piecesList.add(kingOne);
            piecesList.add(kingTwo);
            piecesList.add(defenderOne);
            piecesList.add(defenderTwo);
        }

        return piecesList;
    }
}

package com.telegame.code.builder;

import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.kingolaser.LaserBeam;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MatchBuilder {
    public static GameMatch createMatch(Player player1, String matchName, String password, boolean isPublic) throws NoSuchAlgorithmException {
        GameMatch match = new GameMatch();


        return match;
    }

//    public static List<Piece> getBoardDisposition(Player player1, Player player2) {
//        List<Piece> piecesList = new ArrayList<>();
//
//        Piece deflector = PieceBuilder.buildPiece("deflector", 5,7, Piece.Direction.NORTH);
//        piecesList.add(deflector);
//        Piece bouncer = PieceBuilder.buildPiece("bouncer", 5,3, Piece.Direction.NORTH);
//        piecesList.add(bouncer);
//        Piece king = PieceBuilder.buildPiece("king", 5,0, Piece.Direction.EAST);
//        piecesList.add(king);
//        Piece defender = PieceBuilder.buildPiece("defender", 2,3, Piece.Direction.NORTH);
//        piecesList.add(defender);
//
//        return piecesList;
//    }
}

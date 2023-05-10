package com.telegame.code.builder.games.kingolaser;

import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.models.games.kingolaser.LaserBoard;
import com.telegame.code.models.games.kingolaser.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class KingOLaserBoardBuilder {

    public static LaserBoard getBoardDisposition(String metadata) {
        List<Piece> piecesList = switch (metadata) {
            case "A" -> getBoardDispositionA();
            case "B" -> getBoardDispositionB();
            case "C" -> getBoardDispositionC();
            default -> throw new MatchInfoException();
        };
        return LaserBoard.builder()
                .pieceList(piecesList)
                .build();
    }

    private static List<Piece> getBoardDispositionA() {
        List<Piece> piecesList = new ArrayList<>();
        Piece kingOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.King", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
        Piece kingTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.King", Piece.Owner.PLAYER_TWO, 4, 4, Piece.Direction.NORTH);
        Piece defenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.Defender", Piece.Owner.PLAYER_ONE, 2, 7, Piece.Direction.NORTH);
        Piece defenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.Defender", Piece.Owner.PLAYER_TWO, 6, 6, Piece.Direction.NORTH);
        Piece laserOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.Laser", Piece.Owner.PLAYER_ONE, 9, 7, Piece.Direction.NORTH);
        Piece laserTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.Laser", Piece.Owner.PLAYER_TWO, 0, 0, Piece.Direction.SOUTH);
        Piece deflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.Deflector", Piece.Owner.PLAYER_ONE, 5, 7, Piece.Direction.NORTH);

        piecesList.add(kingOne);
        piecesList.add(kingTwo);
        piecesList.add(defenderOne);
        piecesList.add(defenderTwo);
        piecesList.add(laserOne);
        piecesList.add(laserTwo);
        piecesList.add(deflectorOne);

        return piecesList;
    }

    private static List<Piece> getBoardDispositionB() {
        List<Piece> piecesList = new ArrayList<>();
        Piece kingOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.King", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
        piecesList.add(kingOne);

        return piecesList;
    }

    private static List<Piece> getBoardDispositionC() {
        List<Piece> piecesList = new ArrayList<>();
        Piece kingOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.kingolaser.pieces.King", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
        piecesList.add(kingOne);

        return piecesList;
    }


}

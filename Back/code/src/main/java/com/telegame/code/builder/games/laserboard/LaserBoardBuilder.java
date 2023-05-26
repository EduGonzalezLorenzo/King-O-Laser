package com.telegame.code.builder.games.laserboard;

import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.games.laserboard.LaserBoard;
import com.telegame.code.models.games.laserboard.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class LaserBoardBuilder {

    public static LaserBoard getKingOLaserBoard(GameMatch newGameMatch, String metadata) {
        List<Piece> piecesList = switch (metadata) {
            case "ace" -> getBoardDispositionAce();
            case "B" -> getBoardDispositionB();
            case "C" -> getBoardDispositionC();
            default -> throw new MatchInfoException();
        };

        LaserBoard laserBoard = LaserBoard.builder()
                .pieceList(piecesList)
                .build();
        laserBoard.setGameMatch(newGameMatch);
        laserBoard.setStatus(Board.MatchStatus.WAITING);
        laserBoard.setLastAction("Waiting for start");

        return laserBoard;
    }

    private static List<Piece> getBoardDispositionAce() {
        List<Piece> pieceList = generatePlayerOneAcePieces();
        pieceList.addAll(generatePlayerTwoAcePieces());
        return pieceList;
    }

    private static List<Piece> generatePlayerTwoAcePieces() {
        List<Piece> playerTwoAcePieces = new ArrayList<>();

        playerTwoAcePieces.add(PieceBuilder.buildPiece("Defender", Piece.Owner.PLAYER_TWO, 6, 0, Piece.Direction.WEST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Defender", Piece.Owner.PLAYER_TWO, 4, 0, Piece.Direction.WEST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Bouncer", Piece.Owner.PLAYER_TWO, 4, 3, Piece.Direction.NORTH));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Bouncer", Piece.Owner.PLAYER_TWO, 5, 3, Piece.Direction.EAST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 0, 3, Piece.Direction.NORTH));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 0, 4, Piece.Direction.WEST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 7, 3, Piece.Direction.WEST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 7, 4, Piece.Direction.NORTH));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 6, 5, Piece.Direction.WEST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 2, 1, Piece.Direction.NORTH));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_TWO, 7, 0, Piece.Direction.WEST));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("King", Piece.Owner.PLAYER_TWO, 5, 0, Piece.Direction.NORTH));
        playerTwoAcePieces.add(PieceBuilder.buildPiece("Laser", Piece.Owner.PLAYER_TWO, 0, 0, Piece.Direction.EAST));

        return playerTwoAcePieces;
    }

    private static List<Piece> generatePlayerOneAcePieces() {
        List<Piece> playerOneAcePieces = new ArrayList<>();

        playerOneAcePieces.add(PieceBuilder.buildPiece("Defender", Piece.Owner.PLAYER_ONE, 3, 7, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Bouncer", Piece.Owner.PLAYER_ONE, 4, 4, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Bouncer", Piece.Owner.PLAYER_ONE, 5, 4, Piece.Direction.NORTH));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Defender", Piece.Owner.PLAYER_ONE, 5, 7, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 9, 4, Piece.Direction.SOUTH));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 9, 3, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 2, 7, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 2, 4, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 2, 3, Piece.Direction.SOUTH));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 3, 2, Piece.Direction.EAST));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Deflector", Piece.Owner.PLAYER_ONE, 7, 6, Piece.Direction.SOUTH));
        playerOneAcePieces.add(PieceBuilder.buildPiece("King", Piece.Owner.PLAYER_ONE, 4, 7, Piece.Direction.NORTH));
        playerOneAcePieces.add(PieceBuilder.buildPiece("Laser", Piece.Owner.PLAYER_ONE, 9, 7, Piece.Direction.WEST));

        return playerOneAcePieces;
    }

    private static List<Piece> getBoardDispositionB() {
        List<Piece> piecesList = new ArrayList<>();
        Piece kingOne = PieceBuilder.buildPiece("King", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
        piecesList.add(kingOne);

        return piecesList;
    }

    private static List<Piece> getBoardDispositionC() {
        List<Piece> piecesList = new ArrayList<>();
        Piece kingOne = PieceBuilder.buildPiece("King", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
        piecesList.add(kingOne);

        return piecesList;
    }


}

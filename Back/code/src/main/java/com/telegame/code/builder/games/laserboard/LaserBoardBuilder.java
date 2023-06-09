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
            case "ace" -> getBoardDispositionA();
            case "cur" -> getBoardDispositionB();
            case "gr" -> getBoardDispositionC();
            case "sh" -> getBoardDispositionD();
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

    private static List<Piece> getBoardDispositionA() {
        List<Piece> piecesList = new ArrayList<>();

        Piece blueDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 3, 7, Piece.Direction.EAST);
        Piece blueDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 5, 7, Piece.Direction.EAST);
        Piece blueBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 4, 4, Piece.Direction.EAST);
        Piece blueBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 5, 4, Piece.Direction.NORTH);
        Piece blueDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 4, Piece.Direction.SOUTH);
        Piece blueDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 3, Piece.Direction.EAST);
        Piece blueDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 2, 7, Piece.Direction.EAST);
        Piece blueDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 2, 4, Piece.Direction.EAST);
        Piece blueDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 2, 3, Piece.Direction.SOUTH);
        Piece blueDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 3, 2, Piece.Direction.EAST);
        Piece blueDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 7, 6, Piece.Direction.SOUTH);
        Piece blueKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_ONE, 4, 7, Piece.Direction.NORTH);
        Piece blueLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_ONE, 9, 7, Piece.Direction.WEST);

        Piece redDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 6, 0, Piece.Direction.WEST);
        Piece redDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 4, 0, Piece.Direction.WEST);
        Piece redBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 4, 3, Piece.Direction.NORTH);
        Piece redBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 5, 3, Piece.Direction.EAST);
        Piece redDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 3, Piece.Direction.NORTH);
        Piece redDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 4, Piece.Direction.WEST);
        Piece redDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 7, 3, Piece.Direction.WEST);
        Piece redDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 7, 4, Piece.Direction.NORTH);
        Piece redDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 6, 5, Piece.Direction.WEST);
        Piece redDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 2, 1, Piece.Direction.NORTH);
        Piece redDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 7, 0, Piece.Direction.WEST);
        Piece redKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_TWO, 5, 0, Piece.Direction.NORTH);
        Piece redLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_TWO, 0, 0, Piece.Direction.EAST);

        piecesList.add(blueDefenderOne);
        piecesList.add(blueDefenderTwo);
        piecesList.add(blueBouncerOne);
        piecesList.add(blueBouncerTwo);
        piecesList.add(blueDeflectorOne);
        piecesList.add(blueDeflectorTwo);
        piecesList.add(blueDeflectorThree);
        piecesList.add(blueDeflectorFour);
        piecesList.add(blueDeflectorFive);
        piecesList.add(blueDeflectorSix);
        piecesList.add(blueDeflectorSeven);
        piecesList.add(blueKing);
        piecesList.add(blueLaser);

        piecesList.add(redDefenderOne);
        piecesList.add(redDefenderTwo);
        piecesList.add(redBouncerOne);
        piecesList.add(redBouncerTwo);
        piecesList.add(redDeflectorOne);
        piecesList.add(redDeflectorTwo);
        piecesList.add(redDeflectorThree);
        piecesList.add(redDeflectorFour);
        piecesList.add(redDeflectorFive);
        piecesList.add(redDeflectorSix);
        piecesList.add(redDeflectorSeven);
        piecesList.add(redKing);
        piecesList.add(redLaser);

        return piecesList;
    }

    private static List<Piece> getBoardDispositionB() {
        List<Piece> piecesList = new ArrayList<>();
        Piece blueDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 3, 7, Piece.Direction.EAST);
        Piece blueDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 5, 7, Piece.Direction.EAST);
        Piece blueBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 2, 7, Piece.Direction.EAST);
        Piece blueBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 4, 4, Piece.Direction.EAST);
        Piece blueDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 1, 3, Piece.Direction.SOUTH);
        Piece blueDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 1, 4, Piece.Direction.EAST);
        Piece blueDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 3, 2, Piece.Direction.EAST);
        Piece blueDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 3, 5, Piece.Direction.SOUTH);
        Piece blueDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 4, 3, Piece.Direction.WEST);
        Piece blueDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 3, Piece.Direction.EAST);
        Piece blueDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 4, Piece.Direction.SOUTH);
        Piece blueKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_ONE, 4, 7, Piece.Direction.NORTH);
        Piece blueLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_ONE, 9, 7, Piece.Direction.WEST);

        Piece redDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 6, 0, Piece.Direction.WEST);
        Piece redDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 4, 0, Piece.Direction.WEST);
        Piece redBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 7, 0, Piece.Direction.WEST);
        Piece redBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 5, 3, Piece.Direction.EAST);
        Piece redDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 5, 4, Piece.Direction.EAST);
        Piece redDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 6, 2, Piece.Direction.NORTH);
        Piece redDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 6, 5, Piece.Direction.WEST);
        Piece redDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 8, 3, Piece.Direction.WEST);
        Piece redDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 8, 4, Piece.Direction.NORTH);
        Piece redDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 3, Piece.Direction.NORTH);
        Piece redDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 4, Piece.Direction.WEST);
        Piece redKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_TWO, 5, 0, Piece.Direction.NORTH);
        Piece redLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_TWO, 0, 0, Piece.Direction.EAST);

        piecesList.add(blueDefenderOne);
        piecesList.add(blueDefenderTwo);
        piecesList.add(blueBouncerOne);
        piecesList.add(blueBouncerTwo);
        piecesList.add(blueDeflectorOne);
        piecesList.add(blueDeflectorTwo);
        piecesList.add(blueDeflectorThree);
        piecesList.add(blueDeflectorFour);
        piecesList.add(blueDeflectorFive);
        piecesList.add(blueDeflectorSix);
        piecesList.add(blueDeflectorSeven);
        piecesList.add(blueKing);
        piecesList.add(blueLaser);

        piecesList.add(redDefenderOne);
        piecesList.add(redDefenderTwo);
        piecesList.add(redBouncerOne);
        piecesList.add(redBouncerTwo);
        piecesList.add(redDeflectorOne);
        piecesList.add(redDeflectorTwo);
        piecesList.add(redDeflectorThree);
        piecesList.add(redDeflectorFour);
        piecesList.add(redDeflectorFive);
        piecesList.add(redDeflectorSix);
        piecesList.add(redDeflectorSeven);
        piecesList.add(redKing);
        piecesList.add(redLaser);

        return piecesList;
    }

    private static List<Piece> getBoardDispositionC() {
        List<Piece> piecesList = new ArrayList<>();
        Piece blueDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 4, 7, Piece.Direction.EAST);
        Piece blueDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 4, 5, Piece.Direction.EAST);
        Piece blueBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 3, 5, Piece.Direction.EAST);
        Piece blueBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 7, 4, Piece.Direction.NORTH);
        Piece blueDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 3, 7, Piece.Direction.EAST);
        Piece blueDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 5, 7, Piece.Direction.NORTH);
        Piece blueDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 4, 3, Piece.Direction.EAST);
        Piece blueDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 6, 3, Piece.Direction.WEST);
        Piece blueDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 4, Piece.Direction.EAST);
        Piece blueDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 5, Piece.Direction.SOUTH);
        Piece blueDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 5, 5, Piece.Direction.NORTH);
        Piece blueKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_ONE, 4, 6, Piece.Direction.NORTH);
        Piece blueLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_ONE, 9, 7, Piece.Direction.WEST);

        Piece redDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 5, 0, Piece.Direction.WEST);
        Piece redDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 5, 2, Piece.Direction.WEST);
        Piece redBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 2, 3, Piece.Direction.NORTH);
        Piece redBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 6, 2, Piece.Direction.EAST);
        Piece redDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 4, 0, Piece.Direction.SOUTH);
        Piece redDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 6, 0, Piece.Direction.WEST);
        Piece redDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 4, 2, Piece.Direction.SOUTH);
        Piece redDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 3, 4, Piece.Direction.EAST);
        Piece redDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 5, 4, Piece.Direction.WEST);
        Piece redDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 2, Piece.Direction.NORTH);
        Piece redDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 3, Piece.Direction.WEST);
        Piece redKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_TWO, 5, 1, Piece.Direction.NORTH);
        Piece redLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_TWO, 0, 0, Piece.Direction.EAST);

        piecesList.add(blueDefenderOne);
        piecesList.add(blueDefenderTwo);
        piecesList.add(blueBouncerOne);
        piecesList.add(blueBouncerTwo);
        piecesList.add(blueDeflectorOne);
        piecesList.add(blueDeflectorTwo);
        piecesList.add(blueDeflectorThree);
        piecesList.add(blueDeflectorFour);
        piecesList.add(blueDeflectorFive);
        piecesList.add(blueDeflectorSix);
        piecesList.add(blueDeflectorSeven);
        piecesList.add(blueKing);
        piecesList.add(blueLaser);

        piecesList.add(redDefenderOne);
        piecesList.add(redDefenderTwo);
        piecesList.add(redBouncerOne);
        piecesList.add(redBouncerTwo);
        piecesList.add(redDeflectorOne);
        piecesList.add(redDeflectorTwo);
        piecesList.add(redDeflectorThree);
        piecesList.add(redDeflectorFour);
        piecesList.add(redDeflectorFive);
        piecesList.add(redDeflectorSix);
        piecesList.add(redDeflectorSeven);
        piecesList.add(redKing);
        piecesList.add(redLaser);

        return piecesList;
    }

    private static List<Piece> getBoardDispositionD() {
        List<Piece> piecesList = new ArrayList<>();

        Piece blueDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 4, 6, Piece.Direction.SOUTH);
        Piece blueDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_ONE, 6, 6, Piece.Direction.EAST);
        Piece blueBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 2, 4, Piece.Direction.NORTH);
        Piece blueBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_ONE, 7, 2, Piece.Direction.EAST);
        Piece blueDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 3, 7, Piece.Direction.EAST);
        Piece blueDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 4, 5, Piece.Direction.EAST);
        Piece blueDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 5, 0, Piece.Direction.EAST);
        Piece blueDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 5, 5, Piece.Direction.NORTH);
        Piece blueDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 1, Piece.Direction.SOUTH);
        Piece blueDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 2, Piece.Direction.EAST);
        Piece blueDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_ONE, 9, 5, Piece.Direction.SOUTH);
        Piece blueKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_ONE, 5, 7, Piece.Direction.NORTH);
        Piece blueLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_ONE, 9, 7, Piece.Direction.WEST);

        Piece redDefenderOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 3, 1, Piece.Direction.WEST);
        Piece redDefenderTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Defender", Piece.Owner.PLAYER_TWO, 5, 1, Piece.Direction.NORTH);
        Piece redBouncerOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 2, 5, Piece.Direction.WEST);
        Piece redBouncerTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Bouncer", Piece.Owner.PLAYER_TWO, 7, 3, Piece.Direction.NORTH);
        Piece redDeflectorOne = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 2, Piece.Direction.NORTH);
        Piece redDeflectorTwo = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 5, Piece.Direction.WEST);
        Piece redDeflectorThree = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 0, 6, Piece.Direction.NORTH);
        Piece redDeflectorFour = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 4, 7, Piece.Direction.WEST);
        Piece redDeflectorFive = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 4, 2, Piece.Direction.SOUTH);
        Piece redDeflectorSix = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 6, 0, Piece.Direction.WEST);
        Piece redDeflectorSeven = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Deflector", Piece.Owner.PLAYER_TWO, 5, 2, Piece.Direction.WEST);
        Piece redKing = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.King", Piece.Owner.PLAYER_TWO, 4, 0, Piece.Direction.NORTH);
        Piece redLaser = PieceBuilder.buildPiece("class com.telegame.code.models.games.laserboard.pieces.Laser", Piece.Owner.PLAYER_TWO, 0, 0, Piece.Direction.EAST);

        piecesList.add(blueDefenderOne);
        piecesList.add(blueDefenderTwo);
        piecesList.add(blueBouncerOne);
        piecesList.add(blueBouncerTwo);
        piecesList.add(blueDeflectorOne);
        piecesList.add(blueDeflectorTwo);
        piecesList.add(blueDeflectorThree);
        piecesList.add(blueDeflectorFour);
        piecesList.add(blueDeflectorFive);
        piecesList.add(blueDeflectorSix);
        piecesList.add(blueDeflectorSeven);
        piecesList.add(blueKing);
        piecesList.add(blueLaser);

        piecesList.add(redDefenderOne);
        piecesList.add(redDefenderTwo);
        piecesList.add(redBouncerOne);
        piecesList.add(redBouncerTwo);
        piecesList.add(redDeflectorOne);
        piecesList.add(redDeflectorTwo);
        piecesList.add(redDeflectorThree);
        piecesList.add(redDeflectorFour);
        piecesList.add(redDeflectorFive);
        piecesList.add(redDeflectorSix);
        piecesList.add(redDeflectorSeven);
        piecesList.add(redKing);
        piecesList.add(redLaser);

        return piecesList;
    }


}

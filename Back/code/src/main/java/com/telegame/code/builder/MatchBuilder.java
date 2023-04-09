package com.telegame.code.builder;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.models.Match;
import com.telegame.code.models.Player;
import com.telegame.code.models.kingolaser.LaserBeam;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MatchBuilder {
    public static Match createMatch(Player player1, String matchName, String password, boolean isPublic) throws NoSuchAlgorithmException {
        Match match = new Match();
        match.setName(matchName);
        match.setPassword(password);
        match.setPlayerOne(player1);

        //Para poder asignar las piezas, en el get se sobreescribirá
        Player player2 = new Player();
        match.setPlayerTwo(player2);

        List<Piece> piecesList = getBoardDisposition(player1, player2);
        LaserBeam laserBeam = new LaserBeam();
        LaserBoard laserBoard = new LaserBoard(piecesList, laserBeam);

        match.setBoard(laserBoard);
        match.setIsPublic(isPublic);
        match.setStatus(Match.MatchStatus.WAITING);

        return match;
    }

    public static List<Piece> getBoardDisposition(Player player1, Player player2) {
        List<Piece> piecesList = new ArrayList<>();

        Piece deflector = PieceBuilder.buildPiece(player1, "deflector", 5,7, Piece.Direction.NORTH);
        piecesList.add(deflector);
        Piece bouncer = PieceBuilder.buildPiece(player1, "bouncer", 5,3, Piece.Direction.NORTH);
        piecesList.add(bouncer);
        Piece king = PieceBuilder.buildPiece(player2, "king", 5,0, Piece.Direction.EAST);
        piecesList.add(king);
        Piece defender = PieceBuilder.buildPiece(player2, "defender", 2,3, Piece.Direction.NORTH);
        piecesList.add(defender);

        return piecesList;
    }
}

package com.telegame.code.builder;

import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.Player_Play_Match;
import com.telegame.code.models.kingolaser.LaserBeam;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.King;
import com.telegame.code.models.kingolaser.pieces.Piece;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MatchBuilder {
    public static GameMatch createMatch(Player playerOne, String matchName, String password, boolean isPublic) throws NoSuchAlgorithmException {

        Player_Play_Match player1_play_match = new Player_Play_Match();
        player1_play_match.setPlayer(playerOne);
        List<Player_Play_Match> players = new ArrayList<>();
        players.add(player1_play_match);

        List<Piece> boardDisposition = getBoardDisposition("std");
        LaserBoard laserBoard = new LaserBoard();
        laserBoard.setPieceList(boardDisposition);

        return GameMatch.builder()
                .name(matchName)
                .password(password)
                .isPublic(isPublic)
                .players(players)
                .board(laserBoard)
                .build();
    }

    public static List<Piece> getBoardDisposition(String disposition) {
        List<Piece> piecesList = new ArrayList<>();
        if ("std".equals(disposition)) {
            PieceBuilder.buildPiece("king", Piece.Owner.PLAYER_ONE, 3, 3, Piece.Direction.NORTH);
            PieceBuilder.buildPiece("king", Piece.Owner.PLAYER_TWO, 4, 4, Piece.Direction.NORTH);

            PieceBuilder.buildPiece("defender", Piece.Owner.PLAYER_ONE, 5, 5, Piece.Direction.NORTH);
            PieceBuilder.buildPiece("defender", Piece.Owner.PLAYER_TWO, 6, 6, Piece.Direction.NORTH);
        }

        return piecesList;
    }
}

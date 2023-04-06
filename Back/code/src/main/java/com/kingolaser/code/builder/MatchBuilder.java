package com.kingolaser.code.builder;

import com.kingolaser.code.models.Match;
import com.kingolaser.code.models.Player;

public class MatchBuilder {
    public static Match createMatch(Player player1, String matchName, boolean isOnline, boolean isPublic) {
        Match match = new Match();

        match.setOnline(isOnline);
        match.setPublic(isPublic);
        match.setPlayerOne(player1);
        match.setName(matchName);
        match.setStatus("NOT_READY");
        match.setPieceList(PieceBuilder.getStartPieceList());

        return match;
    }
}

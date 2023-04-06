package com.telegame.code.builder;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.models.Match;
import com.telegame.code.models.Player;

import java.security.NoSuchAlgorithmException;

public class MatchBuilder {
    public static Match createMatch(Player player1, String matchName, String password, boolean isPublic) throws NoSuchAlgorithmException {
        Match match = new Match();

        match.setPublic(isPublic);
        match.setPlayerOne(player1);
        match.setName(matchName);
        match.setPassword(HashUtils.getHashSHA256(password));
        match.setStatus(Match.matchStatus.WAITING);

        return match;
    }
}

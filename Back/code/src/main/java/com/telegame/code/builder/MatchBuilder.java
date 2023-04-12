package com.telegame.code.builder;

import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;

import java.security.NoSuchAlgorithmException;

public class MatchBuilder {
    public static GameMatch createMatch(Player player1, String matchName, String password, boolean isPublic) throws NoSuchAlgorithmException {
        GameMatch match = new GameMatch();


        return match;
    }
}

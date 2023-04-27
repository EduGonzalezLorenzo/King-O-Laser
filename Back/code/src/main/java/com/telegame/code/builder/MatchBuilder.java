package com.telegame.code.builder;

import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player_Play_Match;

import java.util.ArrayList;
import java.util.List;

public class MatchBuilder {

    public static GameMatch fromForm(MatchForm matchForm,
                                     Board board, Player_Play_Match ppm) {

        List<Player_Play_Match> players = new ArrayList<>();
        players.add(ppm);

        return GameMatch.builder()
                .name(matchForm.getMatchName())
                .password(matchForm.getPassword())
                .isPublic(matchForm.getIsPublic())
                .players(players)
                .board(board)
                .build();
    }
}

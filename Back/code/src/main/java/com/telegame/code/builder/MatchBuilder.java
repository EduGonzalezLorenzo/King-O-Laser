package com.telegame.code.builder;

import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.Player_Play_Match;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MatchBuilder {

    public static GameMatch fromForm(MatchForm matchForm,
                                     Board board, Player_Play_Match ppm) {

        List<Player_Play_Match> players = new ArrayList<>();
        players.add(ppm);

        return GameMatch.builder()
                .name(matchForm.getName())
                .password(matchForm.getPassword())
                .isPublic(matchForm.getIsPublic())
                .players(players)
                .board(board)
                .build();
    }
}

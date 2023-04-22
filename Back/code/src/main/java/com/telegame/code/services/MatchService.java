package com.telegame.code.services;

import com.telegame.code.builder.MatchBuilder;
import com.telegame.code.exceptions.InputPlayerFormException;
import com.telegame.code.exceptions.MatchFormException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.*;
import com.telegame.code.repos.MatchRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MatchService {

    private ValidatorFactory validatorFactory;
    private MatchRepo matchRepo;

    public GameMatch createMatch(MatchForm matchForm, Player playerOne,
                              Board board, Player_Play_Match ppm) {

//        Set<ConstraintViolation<MatchForm>> formErrorList = validatorFactory.getValidator().validate(matchForm);
//        if (!formErrorList.isEmpty()) throw new MatchFormException();

        GameMatch gameMatch = MatchBuilder.fromForm(playerOne, matchForm, board, ppm);
        matchRepo.save(gameMatch);
        return gameMatch;
    }



    public List<GameMatch> getMatchList() {
        List<GameMatch> matchList = new ArrayList<>();
        return matchList;
    }

    public GameMatch getMatch(Long matchId) {
        return matchRepo.getReferenceById(matchId);
    }

    public List<GameMatch> updateMatch(Long matchId, Object candidate) {
        List<GameMatch> matchList = new ArrayList<>();
        return matchList;
    }

    public Message deleteMatch(Long matchId, Object candidate) {
        return new Message();
    }

    public String joinGameMatch(Long matchId, Player playerTwo) {

        GameMatch gameMatch = matchRepo.getReferenceById(matchId);
        List<Player_Play_Match> players = gameMatch.getPlayers();

        Player_Play_Match player_play_match = new Player_Play_Match();
        player_play_match.setPlayer(playerTwo);
        player_play_match.setGameMatch(gameMatch);
        players.add(player_play_match);

        gameMatch.setPlayers(players);

        //UPDATE

        return "OK";
    }
}

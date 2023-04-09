package com.telegame.code.services;

import com.telegame.code.builder.MatchBuilder;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.Match;
import com.telegame.code.models.Message;
import com.telegame.code.models.Player;
import com.telegame.code.repos.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    MatchRepo matchRepo;

    public Message createMatch(MatchForm matchForm) throws NoSuchAlgorithmException {
        Player player1 = new Player(); //Player JWT
        player1.setName("Edu");

        Match match = MatchBuilder.createMatch(player1, matchForm.getName(), "password", matchForm.getPublic());
        matchRepo.save(match);
        return new Message();
    }

    public Match getMatch(Long matchId, Player candidate) {
        Match match = matchRepo.getReferenceById(matchId);
        match.setPlayerTwo(candidate);
        match.setStatus(Match.MatchStatus.PLAYER_ONE_TURN);
        return match;
    }

    public List<Match> getMatchList() {
        List<Match> matchList = new ArrayList<>();
        return matchList;
    }

    public List<Match> updateMatch(Long matchId, Object candidate) {
        List<Match> matchList = new ArrayList<>();
        return matchList;
    }

    public Message deleteMatch(Long matchId, Object candidate) {
        return new Message();
    }
}

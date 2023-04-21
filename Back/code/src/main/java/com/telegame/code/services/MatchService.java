package com.telegame.code.services;

import com.telegame.code.builder.MatchBuilder;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Message;
import com.telegame.code.models.Player;
import com.telegame.code.repos.MatchRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    private MatchRepo matchRepo;
//    private MatchBuilder matchBuilder;
    public String createMatch(MatchForm matchForm) {
        // Recoger player del JWT
        Player playerOne = new Player();
        matchRepo.save(MatchBuilder.fromForm(playerOne, matchForm));
        return "Ok";
    }

    public List<GameMatch> getMatchList() {
        List<GameMatch> matchList = new ArrayList<>();
        return matchList;
    }

    public GameMatch getMatch(Long matchId, Player candidate) {
        GameMatch match = matchRepo.getReferenceById(matchId);
        return match;
    }

    public List<GameMatch> updateMatch(Long matchId, Object candidate) {
        List<GameMatch> matchList = new ArrayList<>();
        return matchList;
    }

    public Message deleteMatch(Long matchId, Object candidate) {
        return new Message();
    }
}

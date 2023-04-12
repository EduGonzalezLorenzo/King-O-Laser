package com.telegame.code.services;

import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    public Message createMatch(MatchForm matchForm) {
        return new Message();
    }

    public List<GameMatch> getMatchList() {
        List<GameMatch> matchList = new ArrayList<>();
        return matchList;
    }

    public GameMatch getMatch(Long matchId, Object candidate) {
        GameMatch match = new GameMatch();
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

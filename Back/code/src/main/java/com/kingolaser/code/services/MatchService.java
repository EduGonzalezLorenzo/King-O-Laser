package com.kingolaser.code.services;

import com.kingolaser.code.forms.MatchForm;
import com.kingolaser.code.models.Match;
import com.kingolaser.code.models.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    public Message createMatch(MatchForm matchForm) {
        return new Message();
    }

    public List<Match> getMatchList() {
        List<Match> matchList = new ArrayList<>();
        return matchList;
    }

    public Match getMatch(Long matchId, Object candidate) {
        Match match = new Match();
        return match;
    }

    public List<Match> updateMatch(Long matchId, Object candidate) {
        List<Match> matchList = new ArrayList<>();
        return matchList;
    }

    public Message deleteMatch(Long matchId, Object candidate) {
        return new Message();
    }
}

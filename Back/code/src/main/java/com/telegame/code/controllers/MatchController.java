package com.telegame.code.controllers;

import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.Match;
import com.telegame.code.models.Message;
import com.telegame.code.services.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchController {
    MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/match")
    public Message createMatch(@RequestBody MatchForm matchForm, HttpServletRequest request) {
        return matchService.createMatch(matchForm);
    }

    @GetMapping("/matchList")
    public List<Match> getMatchList() {
        return matchService.getMatchList();
    }

    @GetMapping("/match/{matchId}")
    public Match joinMatch(@PathVariable Long matchId, HttpServletRequest request) {
        return matchService.getMatch(matchId, request.getAttribute("player"));
    }

    @PutMapping("/match/{matchId}")
    public List<Match> updateMatch(@PathVariable Long matchId, HttpServletRequest request) {
        return matchService.updateMatch(matchId, request.getAttribute("player"));
    }

    @DeleteMapping("/match/{matchId}")
    public Message deleteMatch(@PathVariable Long matchId, HttpServletRequest request) {
        return matchService.deleteMatch(matchId, request.getAttribute("player"));
    }
}

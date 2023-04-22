package com.telegame.code.controllers;

import com.telegame.code.exceptions.EmailException;
import com.telegame.code.exceptions.InputPlayerFormException;
import com.telegame.code.exceptions.PlayerNameException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Message;
import com.telegame.code.models.Player;
import com.telegame.code.models.Player_Play_Match;
import com.telegame.code.services.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
public class MatchController {
    MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/match/kingolaser")
    public ResponseEntity<String> createMatch(@RequestBody MatchForm matchForm, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(matchService.createMatch(matchForm), HttpStatus.OK);
        } catch (InputPlayerFormException e) {
            return new ResponseEntity<>("Match form error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/matchList")
    public List<GameMatch> getMatchList() {
        return matchService.getMatchList();
    }

    @PostMapping("/match/{matchId}")
    public ResponseEntity<String> joinMatch(@PathVariable Long matchId, HttpServletRequest request) {
        //PlayerTwo de JWT
        Player playerTwo = new Player();

        return new ResponseEntity<>(matchService.joinGameMatch(matchId, playerTwo), HttpStatus.OK);
    }

    @PutMapping("/match/{matchId}")
    public List<GameMatch> updateMatch(@PathVariable Long matchId, HttpServletRequest request) {
        return matchService.updateMatch(matchId, request.getAttribute("player"));
    }

    @DeleteMapping("/match/{matchId}")
    public Message deleteMatch(@PathVariable Long matchId, HttpServletRequest request) {
        return matchService.deleteMatch(matchId, request.getAttribute("player"));
    }
}

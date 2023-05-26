package com.telegame.code.controllers;

import com.telegame.code.DTO.Message;
import com.telegame.code.exceptions.GameNoExistsException;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.*;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.JoinMatchForm;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.services.MatchService;
import com.telegame.code.services.PlayerService;
import com.telegame.code.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin()
@RestController
@AllArgsConstructor
public class MatchController {
    MatchService matchService;
    PlayerService playerService;
    TokenService tokenService;

    @PostMapping("/match")
    public ResponseEntity<Message> createMatch(@RequestBody MatchForm matchForm, HttpServletRequest request) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(Message.builder()
                    .message(matchService.createMatch(matchForm, request.getAttribute("playerName").toString()))
                    .build(), HttpStatus.OK);
        } catch (InputFormException e) {
            return new ResponseEntity<>(Message.builder().message("Match form error").build(), HttpStatus.BAD_REQUEST);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Player no exists").build(), HttpStatus.BAD_REQUEST);
        } catch (MatchInfoException e) {
            return new ResponseEntity<>(Message.builder().message("Game Metadata Error").build(), HttpStatus.CONFLICT);
        } catch (GameNoExistsException e) {
            return new ResponseEntity<>(Message.builder().message("Game no exists").build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/match/{matchId}")
    public ResponseEntity<Message> joinMatch(@RequestBody JoinMatchForm joinMatchForm, @PathVariable Long matchId, HttpServletRequest request) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(Message.builder()
                    .message(matchService.joinMatch(matchId, joinMatchForm, request.getAttribute("playerName").toString()))
                    .build(), HttpStatus.OK);
        } catch (FilledMatchException e) {
            return new ResponseEntity<>(Message.builder().message("The match has no empty seats").build(), HttpStatus.LOCKED);
        } catch (MatchNoExistsException e) {
            return new ResponseEntity<>(Message.builder().message("Match not found").build(), HttpStatus.NOT_FOUND);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Player can no join match because he no exists").build(), HttpStatus.CONFLICT);
        } catch (PlayerAlreadyInMatchException e) {
            return new ResponseEntity<>(Message.builder().message("You are already in this match").build(), HttpStatus.CONFLICT);
        } catch (MatchInfoException e) {
            return new ResponseEntity<>(Message.builder().message("Wrong password").build(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/matches")
    public ResponseEntity<?> getAvailableMatches() {
        try {
            return new ResponseEntity<>(matchService.getAvailableMatches(), HttpStatus.OK);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Player can no join match because he no exists").build(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/match")
    public ResponseEntity<?> getPlayerCurrentMatches(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(matchService.getPlayerCurrentMatches(request.getAttribute("playerName").toString()), HttpStatus.OK);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Player can no join match because he no exists").build(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getMatchInfo(@PathVariable Long matchId, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(matchService.getMatchInfo(matchId, request.getAttribute("playerName").toString()), HttpStatus.OK);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Player can no join match because he no exists").build(), HttpStatus.CONFLICT);
        } catch (PlayerNotInMatchException e) {
            return new ResponseEntity<>(Message.builder().message("The player is not in this match").build(), HttpStatus.CONFLICT);
        } catch (MatchNoExistsException e) {
            return new ResponseEntity<>(Message.builder().message("This match doesn't exists").build(), HttpStatus.NOT_FOUND);
        }
    }
}

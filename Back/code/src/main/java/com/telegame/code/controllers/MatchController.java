package com.telegame.code.controllers;

import com.telegame.code.exceptions.EmailException;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.PlayerNameException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.services.MatchService;
import com.telegame.code.services.PlayerService;
import com.telegame.code.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@CrossOrigin()
@RestController
@AllArgsConstructor
public class MatchController {
    MatchService matchService;
    PlayerService playerService;
    TokenService tokenService;

    @PostMapping("/match")
    public ResponseEntity<String> createMatch(@RequestBody MatchForm matchForm, HttpServletRequest request) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(matchService.createMatch(matchForm, request.getAttribute("playerName").toString()), HttpStatus.OK);
        } catch (InputFormException e) {
            return new ResponseEntity<>("Match form error", HttpStatus.BAD_REQUEST);
        } catch (EmailException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }
}

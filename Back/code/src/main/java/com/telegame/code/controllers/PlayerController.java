package com.telegame.code.controllers;

import com.telegame.code.exceptions.EmailException;
import com.telegame.code.exceptions.InputPlayerFormException;
import com.telegame.code.exceptions.LoginException;
import com.telegame.code.exceptions.PlayerNameException;
import com.telegame.code.forms.LoginForm;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.Player;
import com.telegame.code.services.PlayerService;
import com.telegame.code.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@CrossOrigin()
@RestController
@AllArgsConstructor
public class PlayerController {
    PlayerService playerService;
    TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody PlayerForm playerForm) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(playerService.signUp(playerForm), HttpStatus.OK);
        } catch (InputPlayerFormException e) {
            return new ResponseEntity<>("Player form error", HttpStatus.BAD_REQUEST);
        } catch (EmailException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(playerService.login(loginForm), HttpStatus.OK);
        } catch (InputPlayerFormException e) {
            return new ResponseEntity<>("Player form error", HttpStatus.BAD_REQUEST);
        } catch (LoginException e) {
            return new ResponseEntity<>("Wrong user, email or password already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPlayer")
    public Player getPlayerInfo(HttpServletRequest request) {
        return playerService.getPlayerInfo(request.getAttribute("player"));
    }

    @PutMapping("/update/{PlayerId}")
    public Map<String, String> updatePlayerInfo(@RequestBody PlayerForm playerForm, @PathVariable Long playerId, HttpServletRequest request) {
        return playerService.updatePlayerInfo(playerForm, playerId, request.getAttribute("player"));
    }

    @DeleteMapping("/update/{playerId}")
    public Map<String, String> deletePlayer(@PathVariable Long playerId, HttpServletRequest request) {
        return playerService.deletePlayerInfo(playerId, request.getAttribute("player"));
    }
}

package com.telegame.code.controllers;

import com.telegame.code.DTO.Message;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.player.EmailException;
import com.telegame.code.exceptions.player.LoginException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.LoginForm;
import com.telegame.code.forms.PlayerForm;
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

    @PostMapping("/signUp")
    public ResponseEntity<Message> signUp(@RequestBody PlayerForm playerForm) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(Message.builder()
                    .message(playerService.signUp(playerForm))
                    .build(), HttpStatus.OK);
        } catch (InputFormException e) {
            return new ResponseEntity<>(Message.builder().message("Player form error").build(), HttpStatus.BAD_REQUEST);
        } catch (EmailException e) {
            return new ResponseEntity<>(Message.builder().message("Email already exists").build(), HttpStatus.BAD_REQUEST);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Username already exists").build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginForm loginForm) throws NoSuchAlgorithmException {
        try {
            return new ResponseEntity<>(Message.builder().message(playerService.login(loginForm)).build(), HttpStatus.OK);
        } catch (InputFormException e) {
            return new ResponseEntity<>(Message.builder().message("Player form error").build(), HttpStatus.BAD_REQUEST);
        } catch (LoginException e) {
            return new ResponseEntity<>(Message.builder().message("Wrong user, email or password").build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPlayer")
    public ResponseEntity<Message> getPlayerInfo(HttpServletRequest request) {
        return new ResponseEntity<>(Message.builder()
                .message(playerService.getPlayerInfo(request.getAttribute("playerName").toString()))
                .build(), HttpStatus.OK);
    }

//    @PutMapping("/update/{PlayerId}")
//    public Map<String, String> updatePlayerInfo(@RequestBody PlayerForm playerForm, @PathVariable Long playerId, HttpServletRequest request) {
//        return playerService.updatePlayerInfo(playerForm, playerId, request.getAttribute("player"));
//    }
//
//    @DeleteMapping("/update/{playerId}")
//    public Map<String, String> deletePlayer(@PathVariable Long playerId, HttpServletRequest request) {
//        return playerService.deletePlayerInfo(playerId, request.getAttribute("player"));
//    }
}

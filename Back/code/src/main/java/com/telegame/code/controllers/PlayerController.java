package com.telegame.code.controllers;

import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.Player;
import com.telegame.code.services.PlayerService;
import com.telegame.code.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin()
@RestController
public class PlayerController {
    PlayerService playerService;
    TokenService tokenService;

    public PlayerController(PlayerService playerService, TokenService tokenService) {
        this.playerService = playerService;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    public Map<String, String> signUp(@RequestBody PlayerForm playerForm) {
        return playerService.signUp(playerForm);
    }

    @PostMapping("/login")
    public Player login(@RequestBody PlayerForm playerForm) {
        return playerService.login(playerForm);
    }

    @GetMapping("/getPlayer")
    public Player getPlayerInfo(HttpServletRequest request) {
        //Forma no definitva, hay que estudiar como hacerlo
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

package com.telegame.code.controllers;

import com.telegame.code.exceptions.GameNoExistsException;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.ActionForm;
import com.telegame.code.services.PlayerPlayMatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@AllArgsConstructor
public class PlayerPlayMatchController {
    PlayerPlayMatchService playerPlayMatchService;

    @PostMapping("/match/{matchId}/action")
    public ResponseEntity<String> createMatch(@RequestBody ActionForm actionForm, @PathVariable Long matchId, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(playerPlayMatchService.doAction(actionForm, matchId, request.getAttribute("playerName").toString()), HttpStatus.OK);
        } catch (InputFormException e) {
            return new ResponseEntity<>("Action form error", HttpStatus.BAD_REQUEST);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>("Player no exists", HttpStatus.BAD_REQUEST);
        } catch (MatchInfoException e) {
            return new ResponseEntity<>("Game Metadata Error", HttpStatus.CONFLICT);
        } catch (GameNoExistsException e) {
            return new ResponseEntity<>("Game no exists", HttpStatus.BAD_REQUEST);
        }
    }
}

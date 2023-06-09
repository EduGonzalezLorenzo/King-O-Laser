package com.telegame.code.controllers;

import com.telegame.code.DTO.Message;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.exceptions.match.MatchNoExistsException;
import com.telegame.code.exceptions.match.PieceNotFoundException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.services.PlayerPlayMatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin({"http://localhost:3000/", "https://king-o-laser-front.vercel.app"})
@RestController
@AllArgsConstructor
public class PlayerPlayMatchController {
    PlayerPlayMatchService playerPlayMatchService;

    @PostMapping("/match/{matchId}/action")
    public ResponseEntity<Message> doAction(@RequestBody LaserBoardMoveForm actionForm, @PathVariable Long matchId, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(Message.builder()
                    .message(playerPlayMatchService.doAction(actionForm, matchId, request.getAttribute("playerName").toString()))
                    .build(), HttpStatus.OK);
        } catch (InputFormException e) {
            return new ResponseEntity<>(Message.builder().message("Action form error").build(), HttpStatus.BAD_REQUEST);
        } catch (MatchNoExistsException e) {
            return new ResponseEntity<>(Message.builder().message("Match no exists").build(), HttpStatus.NOT_FOUND);
        } catch (PlayerNameException e) {
            return new ResponseEntity<>(Message.builder().message("Player no exists").build(), HttpStatus.I_AM_A_TEAPOT);
        } catch (MatchInfoException e) {
            return new ResponseEntity<>(Message.builder().message("Database error").build(), HttpStatus.CONFLICT);
        } catch (PieceNotFoundException e) {
            return new ResponseEntity<>(Message.builder().message("There is no piece in this coordinates").build(), HttpStatus.BAD_REQUEST);
        }
    }
}

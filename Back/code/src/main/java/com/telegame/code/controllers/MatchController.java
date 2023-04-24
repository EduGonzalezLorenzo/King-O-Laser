package com.telegame.code.controllers;

import com.telegame.code.builder.BoardBuilder;
import com.telegame.code.exceptions.MatchFormException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.forms.MovementForm;
import com.telegame.code.models.*;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.repos.PlayerRepo;
import com.telegame.code.services.BoardService;
import com.telegame.code.services.MatchService;
import com.telegame.code.services.PPMService;
import com.telegame.code.services.kingolaser.PieceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
public class MatchController {
    MatchService matchService;

    @Autowired
    BoardService boardService;
    @Autowired
    PPMService ppmService;
    @Autowired
    PieceService pieceService;


    @Autowired
    PlayerRepo playerRepo;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }


    @PostMapping("/match/kingolaser")
    public ResponseEntity<String> createMatch(@RequestBody MatchForm matchForm, HttpServletRequest request) {

        Player playerOne = playerRepo.getReferenceById(1L);

        try {
            List<Piece> boardDisposition = BoardBuilder.getBoardDisposition(matchForm.getBoardDisposition());
            Player_Play_Match ppm = ppmService.createPpm(playerOne);
            ppm.setPlayerNumber(Piece.Owner.PLAYER_ONE);
            Board laserBoard = boardService.createBoard(boardDisposition);

            for (Piece piece : boardDisposition) {
                piece.setLaserBoard((LaserBoard)laserBoard);
                pieceService.savePiece(piece);
            }

            GameMatch gameMatch = matchService.createMatch(matchForm, laserBoard, ppm);
            ppm.setGameMatch(gameMatch);
            ppmService.savePPM(ppm);
            laserBoard.setGameMatch(gameMatch);
            boardService.saveBoard(laserBoard);
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        } catch (MatchFormException e) {
            return new ResponseEntity<>("Match form error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/matchList")
    public List<GameMatch> getMatchList() {
        return matchService.getMatchList();
    }

    @PostMapping("/match/kingolaser/{matchId}")
    public ResponseEntity<String> joinMatch(@PathVariable Long matchId, HttpServletRequest request) {
        //PlayerTwo de JWT
        Player playerTwo = playerRepo.getReferenceById(2L);

        return new ResponseEntity<>(matchService.joinGameMatch(matchId, playerTwo), HttpStatus.OK);
    }

    @PutMapping("/match/kingolaser/move/{matchId}")
    public ResponseEntity<String> updateMatch(@PathVariable Long matchId, @RequestBody MovementForm movementForm, HttpServletRequest request) {
        matchService.updateMatch(matchId, movementForm);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/match/{matchId}")
    public Message deleteMatch(@PathVariable Long matchId, HttpServletRequest request) {
        return matchService.deleteMatch(matchId, request.getAttribute("player"));
    }
}

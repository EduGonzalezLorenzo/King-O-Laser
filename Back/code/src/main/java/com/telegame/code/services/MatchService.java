package com.telegame.code.services;

import com.telegame.code.builder.MatchBuilder;
import com.telegame.code.exceptions.InputPlayerFormException;
import com.telegame.code.exceptions.MatchFormException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.forms.MovementForm;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.*;
import com.telegame.code.models.kingolaser.LaserBeam;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.MatchRepo;
import com.telegame.code.repos.PieceRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MatchService {

    private ValidatorFactory validatorFactory;
    private MatchRepo matchRepo;

    private PieceRepo pieceRepo;

    private BoardRepo boardRepo;

    @Autowired
    PPMService ppmService;

    public GameMatch createMatch(MatchForm matchForm,
                                 Board board, Player_Play_Match ppm) {

//        Set<ConstraintViolation<MatchForm>> formErrorList = validatorFactory.getValidator().validate(matchForm);
//        if (!formErrorList.isEmpty()) throw new MatchFormException();

        GameMatch gameMatch = MatchBuilder.fromForm(matchForm, board, ppm);
        matchRepo.save(gameMatch);
        return gameMatch;
    }


    public List<GameMatch> getMatchList() {
        List<GameMatch> matchList = new ArrayList<>();
        return matchList;
    }

    public GameMatch getMatch(Long matchId) {
        return matchRepo.getReferenceById(matchId);
    }

    public ResponseEntity<String> updateMatch(Long matchId, MovementForm movementForm) {
        try {
            GameMatch gameMatch = matchRepo.getReferenceById(matchId);
            Board board = gameMatch.getBoard();
            Board.MatchStatus matchStatus = board.getStatus();

            List<Piece> currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(movementForm.getCurrentPosY(), movementForm.getCurrentPosX(), board.getId());

            Piece piece = currentDisposition.get(0);
            if((matchStatus == Board.MatchStatus.PLAYER_ONE_TURN && piece.getOwner() == Piece.Owner.PLAYER_ONE) ||
                    (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN && piece.getOwner() == Piece.Owner.PLAYER_TWO)) {
                if (movementForm.getRotateTo() == null) {
                    if(!piece.move(movementForm.getNewPosY(), movementForm.getNewPosX())) return new ResponseEntity<>("Incorrect Movement", HttpStatus.BAD_REQUEST);
                } else {
                    if(!piece.rotate(movementForm.getRotateTo(), piece)) return new ResponseEntity<>("Incorrect Rotation Value", HttpStatus.BAD_REQUEST);
                }
            } else {
                System.out.println("Turno incorrecto");
                return new ResponseEntity<>("Wrong turn", HttpStatus.BAD_REQUEST);
            }

            pieceRepo.save(piece);
            boardRepo.save(board);

            if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(9, 7, board.getId());
            if(matchStatus == Board.MatchStatus.PLAYER_TWO_TURN) currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(0, 0, board.getId());

            piece = currentDisposition.get(0);

            currentDisposition = pieceRepo.findByLaserBoardId(board.getId());

            LaserBeam laserBeam = new LaserBeam();
            Map<String, Object> laserResult = laserBeam.shootLaser(matchStatus, piece.getRotation(), currentDisposition);

            System.out.println("LASER RESULT: " + laserResult.get("message"));
            List<int[]> route = (List<int[]>) laserResult.get("route");
            System.out.println("last step: " + Arrays.toString(route.get(route.size() -1)));



//            System.out.println("ROUTE: ");
//            for (int[] step : route) {
//                System.out.print(" [" + step[0] + " : " + step[1] + "]");
//            }
//            System.out.println();



            if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) {
                board.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
            } else {
                board.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
            }

            boardRepo.save(board);

            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Incorrect Movement", HttpStatus.BAD_REQUEST);
        }

    }

    public Message deleteMatch(Long matchId, Object candidate) {
        return new Message();
    }

    public String joinGameMatch(Long matchId, Player playerTwo) {

        GameMatch gameMatch = matchRepo.getReferenceById(matchId);
        List<Player_Play_Match> players = gameMatch.getPlayers();

        Player_Play_Match ppm = ppmService.createPpm(playerTwo);
        ppm.setPlayerNumber(Piece.Owner.PLAYER_TWO);
        ppm.setGameMatch(gameMatch);

        players.add(ppm);

        gameMatch.setPlayers(players);
        Board laserBoard = gameMatch.getBoard();
        laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);

        matchRepo.save(gameMatch);

        return "OK";
    }
}

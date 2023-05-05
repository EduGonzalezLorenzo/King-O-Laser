package com.telegame.code.services;

import com.telegame.code.builder.MatchBuilder;
import com.telegame.code.exceptions.InputPlayerFormException;
import com.telegame.code.exceptions.MatchFormException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.forms.MovementForm;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.*;
import com.telegame.code.models.kingolaser.LaserBeam;
import com.telegame.code.models.kingolaser.pieces.King;
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

    @Autowired
    BoardService boardService;

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

    public Map<String, Object> updateMatch(Long matchId, MovementForm movementForm) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            GameMatch gameMatch = matchRepo.getReferenceById(matchId);
            Board board = gameMatch.getBoard();
            Board.MatchStatus matchStatus = board.getStatus();

            List<Piece> currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(movementForm.getCurrentPosY(), movementForm.getCurrentPosX(), board.getId());

            Piece piece = currentDisposition.get(0);
            if((matchStatus == Board.MatchStatus.PLAYER_ONE_TURN && piece.getOwner() == Piece.Owner.PLAYER_ONE) ||
                    (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN && piece.getOwner() == Piece.Owner.PLAYER_TWO)) {
                if (movementForm.getRotateTo() == null) {
                    if(!piece.move(movementForm.getNewPosY(), movementForm.getNewPosX())) {
                        responseMap.put("message", "Incorrect Movement");
                        responseMap.put("response", HttpStatus.BAD_REQUEST);
                        return responseMap;
                    }
                } else {
                    if(!piece.rotate(movementForm.getRotateTo(), piece)) {
                        responseMap.put("message", "Incorrect Rotation Value");
                        responseMap.put("response", HttpStatus.BAD_REQUEST);
                        return responseMap;
                    }
                }
            } else {
                System.out.println("Turno incorrecto");
                responseMap.put("message", "Wrong turn");
                responseMap.put("response", HttpStatus.BAD_REQUEST);
                return responseMap;
            }

            pieceRepo.save(piece);
            boardRepo.save(board);

            if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(9, 7, board.getId());
            if(matchStatus == Board.MatchStatus.PLAYER_TWO_TURN) currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(0, 0, board.getId());

            piece = currentDisposition.get(0);

            currentDisposition = pieceRepo.findByLaserBoardId(board.getId());

            LaserBeam laserBeam = new LaserBeam();
            Map<String, Object> laserResult = laserBeam.shootLaser(matchStatus, piece.getRotation(), currentDisposition);
            List<int[]> route = (List<int[]>) laserResult.get("route");
            if ("HIT".equals(laserResult.get("message"))) {

                int[] lastStep = route.get(route.size() -1);
                List<Piece> pieceList = pieceRepo.findByPosYAndPosXAndLaserBoardId(lastStep[0], lastStep[1], board.getId());
                piece = pieceList.get(0);
                if(piece.getClass() == King.class) {
                    Piece.Owner owner = piece.getOwner();
                    if(owner == Piece.Owner.PLAYER_ONE) board.setStatus(Board.MatchStatus.PLAYER_TWO_WIN);
                    if(owner == Piece.Owner.PLAYER_TWO) board.setStatus(Board.MatchStatus.PLAYER_ONE_WIN);
                    pieceRepo.delete(piece);
                    currentDisposition = pieceRepo.findByLaserBoardId(board.getId());
                    responseMap.put("message", "ENDGAME");
                    responseMap.put("route", route);
                    responseMap.put("response", HttpStatus.OK);
                    responseMap.put("board", boardService.createBoardMap(currentDisposition));
                    boardRepo.save(board);
                    return responseMap;
                }
                pieceRepo.delete(piece);
                if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) {
                    board.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
                } else {
                    board.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
                }
                boardRepo.save(board);
                currentDisposition = pieceRepo.findByLaserBoardId(board.getId());

                responseMap.put("message", laserResult.get("message"));
                responseMap.put("route", route);
                responseMap.put("response", HttpStatus.OK);
                responseMap.put("board", boardService.createBoardMap(currentDisposition));

                return responseMap;
            }


            if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) {
                board.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
            } else {
                board.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
            }
            boardRepo.save(board);
            currentDisposition = pieceRepo.findByLaserBoardId(board.getId());
            responseMap.put("message", laserResult.get("message"));
            responseMap.put("route", route);
            responseMap.put("response", HttpStatus.OK);
            responseMap.put("board", boardService.createBoardMap(currentDisposition));

            return responseMap;
        } catch (RuntimeException e) {
            responseMap.put("message", "Incorrect Movement");
            responseMap.put("response", HttpStatus.BAD_REQUEST);
            return responseMap;
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

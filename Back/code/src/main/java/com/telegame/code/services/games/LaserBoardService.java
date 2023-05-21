package com.telegame.code.services.games;

import com.telegame.code.DTO.games.laserboard.LaserBoardDTO;
import com.telegame.code.DTO.games.laserboard.PieceDTO;
import com.telegame.code.builder.games.laserboard.LaserBoardBuilder;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.exceptions.match.PieceNotFoundException;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.games.laserboard.LaserBeam;
import com.telegame.code.models.games.laserboard.LaserBoard;
import com.telegame.code.models.games.laserboard.pieces.Piece;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.games.laserboard.PieceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class LaserBoardService {
    private PieceRepo pieceRepo;
    private BoardRepo boardRepo;

    public String movePiece(LaserBoardMoveForm laserBoardMoveForm, Player player, GameMatch gameMatch) {

        Optional<Board> laserBoardOptional = boardRepo.findBoardByGameMatch(gameMatch);
        if (laserBoardOptional.isEmpty()) throw new MatchInfoException();
        LaserBoard laserBoard = (LaserBoard) laserBoardOptional.get();

        Board.MatchStatus matchStatus = laserBoard.getStatus();
        List<Piece> piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(
                laserBoardMoveForm.getCurrentPosY(),
                laserBoardMoveForm.getCurrentPosX(),
                laserBoard.getId());
        if (piecesList.size() == 0) throw new PieceNotFoundException();
        //TODO El siguiente error tiene más implicaciones que hay que contemplar. Si pasara habría que borrar la partida
        if (piecesList.size() != 1) throw new RuntimeException();
        Piece piece = piecesList.get(0);

        if (playerCanMove(matchStatus, piece)) {
            if (laserBoardMoveForm.getRotateTo() == null || laserBoardMoveForm.getRotateTo().equals("")) {
                if (!piece.move(laserBoardMoveForm.getNewPosY(), laserBoardMoveForm.getNewPosX())) {
                    throw new InputFormException();
                }
            } else {
                if (!piece.rotate(laserBoardMoveForm.getRotateTo(), piece)) throw new InputFormException();
            }
        } else throw new InputFormException();

        pieceRepo.save(piece);
        boardRepo.save(laserBoard);

        List<Piece> boardDisposition = pieceRepo.findByLaserBoardId(laserBoard.getId());

        shootLaser(laserBoard, matchStatus, boardDisposition);

        return "Ok";
    }

    private List<int[]> shootLaser(LaserBoard laserBoard, Board.MatchStatus matchStatus, List<Piece> piecesList) {
        Piece piece;
        if (matchStatus == Board.MatchStatus.PLAYER_ONE_TURN)
            piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(9, 7, laserBoard.getId());
        if (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN)
            piecesList = pieceRepo.findByPosYAndPosXAndLaserBoardId(0, 0, laserBoard.getId());

        piece = piecesList.get(0);
        List<Piece> currentDisposition = pieceRepo.findByLaserBoardId(laserBoard.getId());

        LaserBeam laserBeam = new LaserBeam();
        Map<String, Object> laserResult = laserBeam.shootLaser(matchStatus, piece.getRotation(), currentDisposition);

        List<int[]> route = (List<int[]>) laserResult.get("route");

        if (matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) laserBoard.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
        else laserBoard.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);

        laserBoard.setLastAction(formatRoute(route));

        boardRepo.save(laserBoard);

        return route;
    }

    private String formatRoute(List<int[]> route) {
        StringBuilder formattedRoute = new StringBuilder();
        for (int[] coordinate : route) {
            formattedRoute.append(coordinate[0]).append(",").append(coordinate[1]).append("*");
        }
        return formattedRoute.toString();
    }


    private static boolean playerCanMove(Board.MatchStatus matchStatus, Piece piece) {
        return playerOneCanMove(matchStatus, piece) || playerTwoCanMove(matchStatus, piece);
    }

    private static boolean playerOneCanMove(Board.MatchStatus matchStatus, Piece piece) {
        return matchStatus == Board.MatchStatus.PLAYER_ONE_TURN && piece.getOwner() == Piece.Owner.PLAYER_ONE;
    }

    private static boolean playerTwoCanMove(Board.MatchStatus matchStatus, Piece piece) {
        return (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN && piece.getOwner() == Piece.Owner.PLAYER_TWO);
    }

    //TODO (Esta función la tenía en MatchService)

//    public ResponseEntity<String> updateMatch(Long matchId, MovementForm movementForm) {
//        try {
//            GameMatch gameMatch = matchRepo.getReferenceById(matchId);
//            Board board = gameMatch.getBoard();
//            Board.MatchStatus matchStatus = board.getStatus();
//            List<Piece> currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(movementForm.getCurrentPosY(), movementForm.getCurrentPosX(), board.getId());
//            Piece piece = currentDisposition.get(0);
//            if((matchStatus == Board.MatchStatus.PLAYER_ONE_TURN && piece.getOwner() == Piece.Owner.PLAYER_ONE) ||
//                    (matchStatus == Board.MatchStatus.PLAYER_TWO_TURN && piece.getOwner() == Piece.Owner.PLAYER_TWO)) {
//                if (movementForm.getRotateTo() == null) {
//                    if(!piece.move(movementForm.getNewPosY(), movementForm.getNewPosX())) return new ResponseEntity<>("Incorrect Movement", HttpStatus.BAD_REQUEST);
//                } else {
//                    if(!piece.rotate(movementForm.getRotateTo(), piece)) return new ResponseEntity<>("Incorrect Rotation Value", HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                System.out.println("Turno incorrecto");
//                return new ResponseEntity<>("Wrong turn", HttpStatus.BAD_REQUEST);
//            }
//
//            pieceRepo.save(piece);
//
//            boardRepo.save(board);
//            if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(9, 7, board.getId());
//            if(matchStatus == Board.MatchStatus.PLAYER_TWO_TURN) currentDisposition = pieceRepo.findByPosYAndPosXAndLaserBoardId(0, 0, board.getId());
//            piece = currentDisposition.get(0);
//            currentDisposition = pieceRepo.findByLaserBoardId(board.getId());
//
//            LaserBeam laserBeam = new LaserBeam();
//            Map<String, Object> laserResult = laserBeam.shootLaser(matchStatus, piece.getRotation(), currentDisposition);
//
//            System.out.println("LASER RESULT: " + laserResult.get("message"));
//            List<int[]> route = (List<int[]>) laserResult.get("route");
//            System.out.println("last step: " + Arrays.toString(route.get(route.size() -1)));
//
//            if(matchStatus == Board.MatchStatus.PLAYER_ONE_TURN) {
//                board.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);
//            } else {
//                board.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
//            }
//
//            boardRepo.save(board);
//
//            return new ResponseEntity<>("OK", HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>("Incorrect Movement", HttpStatus.BAD_REQUEST);
//        }
//
//    }

    public Board generateBoard(GameMatch newGameMatch, String metadata) {
        return LaserBoardBuilder.getKingOLaserBoard(newGameMatch, metadata);
    }

    public void savePieces(LaserBoard board) {
        for (Piece piece : board.getPieceList()) {
            piece.setLaserBoard(board);
            pieceRepo.save(piece);
        }
    }

    public LaserBoardDTO generateLaserBoardDTO(LaserBoard board) {

        return LaserBoardDTO.builder()
                .pieces(generatePieceListDTO(board.getPieceList()))
                .lastAction(board.getLastAction())
                .build();
    }

    private List<PieceDTO> generatePieceListDTO(List<Piece> pieceList) {
        List<PieceDTO> pieceDTOList = new ArrayList<>();
        for (Piece piece : pieceList) {
            pieceDTOList.add(PieceDTO.builder()
                    .x(piece.getPosX())
                    .y(piece.getPosY())
                    .owner(piece.getOwner().name())
                    .rotation(piece.getRotation().name())
                    .type(piece.getClass().getName())
                    .build());
        }
        return pieceDTOList;
    }
}

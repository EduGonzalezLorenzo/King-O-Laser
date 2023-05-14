package com.telegame.code.services.games;

import com.telegame.code.DTO.games.kingolaser.LaserBoardDTO;
import com.telegame.code.DTO.games.kingolaser.PieceDTO;
import com.telegame.code.builder.games.kingolaser.KingOLaserBoardBuilder;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.games.kingolaser.LaserBoard;
import com.telegame.code.models.games.kingolaser.pieces.Piece;
import com.telegame.code.repos.games.PieceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LaserBoardService {
    private PieceRepo pieceRepo;

    public String movePiece(LaserBoardMoveForm laserBoardMoveForm, Player player, GameMatch gameMatch) {



        return "ok";
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
        return KingOLaserBoardBuilder.getKingOLaserBoard(newGameMatch, metadata);
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

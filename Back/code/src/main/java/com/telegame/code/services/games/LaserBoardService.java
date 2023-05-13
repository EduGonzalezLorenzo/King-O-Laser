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

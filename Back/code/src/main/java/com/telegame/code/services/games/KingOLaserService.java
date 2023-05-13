package com.telegame.code.services.games;

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

@Service
@AllArgsConstructor
public class KingOLaserService {
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
}

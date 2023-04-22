package com.telegame.code.services;

import com.telegame.code.builder.BoardBuilder;
import com.telegame.code.models.Board;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.repos.BoardRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepo boardRepo;

    public Board createBoard(List<Piece> boardDisposition) {
        LaserBoard laserBoard = new LaserBoard();
        laserBoard.setPieceList(boardDisposition);
        laserBoard.setStatus(Board.MatchStatus.WAITING);
        saveBoard(laserBoard);
        return laserBoard;
    }

    public void saveBoard(Board board) {
        boardRepo.save(board);
    }

}

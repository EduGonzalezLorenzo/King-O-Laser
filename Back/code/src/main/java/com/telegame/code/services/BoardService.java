package com.telegame.code.services;

import com.telegame.code.builder.BoardBuilder;
import com.telegame.code.models.Board;
import com.telegame.code.models.kingolaser.LaserBoard;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.repos.BoardRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Object> createBoardMap(List<Piece> currentDisposition) {
        List<Object> boardMap = new ArrayList<>();
        for(Piece piece : currentDisposition) {
            Map<String, Object> pieceMap = new HashMap<>();
            pieceMap.put("class", piece.getClass());
            pieceMap.put("owner", piece.getOwner());
            pieceMap.put("posY", piece.getPosY());
            pieceMap.put("posX", piece.getPosX());
            pieceMap.put("rotation", piece.getRotation());
            boardMap.add(pieceMap);
        }
        return boardMap;
    }

}

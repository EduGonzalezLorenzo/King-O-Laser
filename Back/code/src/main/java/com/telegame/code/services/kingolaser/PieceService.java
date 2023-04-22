package com.telegame.code.services.kingolaser;

import com.telegame.code.models.GameMatch;
import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.repos.PieceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieceService {

    @Autowired
    PieceRepo pieceRepo;


//    public GameMatch movePiece(Object match) {
//        return new GameMatch();
//    }

    public void savePiece(Piece piece) {
        pieceRepo.save(piece);
    }
}

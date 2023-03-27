package com.kingolaser.code.services;

import com.kingolaser.code.forms.PieceForm;
import com.kingolaser.code.models.Match;
import com.kingolaser.code.models.Piece;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PieceService {

    public Match movePiece(PieceForm pieceForm, Object match) {
        return new Match();
    }
}

package com.kingolaser.code.controllers;

import com.kingolaser.code.forms.PieceForm;
import com.kingolaser.code.models.Match;
import com.kingolaser.code.models.Piece;
import com.kingolaser.code.services.PieceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PieceController {
    PieceService pieceService;

    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @PostMapping("match/{matchId}")
    public Match movePiece(@RequestBody PieceForm pieceForm, HttpServletRequest request){
        return pieceService.movePiece(pieceForm, request.getAttribute("match"));
    }
}

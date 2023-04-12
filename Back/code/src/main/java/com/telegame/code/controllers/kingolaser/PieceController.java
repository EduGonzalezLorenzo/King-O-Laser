package com.telegame.code.controllers.kingolaser;

import com.telegame.code.models.GameMatch;
import com.telegame.code.services.kingolaser.PieceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PieceController {
    PieceService pieceService;

    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @PostMapping("match/{matchId}")
    public GameMatch movePiece(HttpServletRequest request){
        return pieceService.movePiece(request.getAttribute("match"));
    }
}

package com.telegame.code.controllers.kingolaser;

import com.telegame.code.forms.MovementForm;
import com.telegame.code.models.Match;
import com.telegame.code.services.kingolaser.PieceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PieceController {
    PieceService pieceService;

    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @PostMapping("match/{matchId}")
    public Match movePiece(@RequestBody MovementForm movementForm, HttpServletRequest request){

        return pieceService.movePiece((Match)request.getAttribute("match"), movementForm);
    }
}

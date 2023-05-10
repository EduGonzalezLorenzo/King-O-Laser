package com.telegame.code.services.games;

import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KingOLaserService {
    public String movePiece(LaserBoardMoveForm laserBoardMoveForm, Player player, GameMatch gameMatch) {
        return "ok";
    }
}

package com.telegame.code.services;

import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.MatchInfoException;
import com.telegame.code.exceptions.match.MatchNoExistsException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.ActionForm;
import com.telegame.code.forms.games.LaserBoardMoveForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.games.kingolaser.LaserBoard;
import com.telegame.code.repos.GameMatchRepo;
import com.telegame.code.repos.PlayerRepo;
import com.telegame.code.services.games.KingOLaserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerPlayMatchService {
    private ValidatorFactory validatorFactory;
    private GameMatchRepo gameMatchRepo;
    private PlayerRepo playerRepo;
    private KingOLaserService kingOLaserService;

    public String doAction(ActionForm actionForm, Long matchId, String playerName) {
        Set<ConstraintViolation<ActionForm>> formErrorList = validatorFactory.getValidator().validate(actionForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();

        Optional<GameMatch> gameMatchOptional = gameMatchRepo.findById(matchId);
        if (gameMatchOptional.isEmpty()) throw new MatchNoExistsException();
        GameMatch gameMatch = gameMatchOptional.get();

        Optional<Player> playerOptional = playerRepo.findByPlayerNameEquals(playerName);
        if (playerOptional.isEmpty()) throw new PlayerNameException();
        Player player = playerOptional.get();

        Board board = gameMatch.getBoard();
        String message;
        if (board instanceof LaserBoard) {
            LaserBoardMoveForm laserBoardMoveForm = (LaserBoardMoveForm) actionForm;
            message = kingOLaserService.movePiece(laserBoardMoveForm, player, gameMatch);
        } else throw new MatchInfoException();

        return message;
    }
}

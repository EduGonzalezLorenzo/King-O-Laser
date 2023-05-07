package com.telegame.code.services;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.exceptions.GameNoExistsException;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.PlayerNameException;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import com.telegame.code.repos.GameMatchRepo;
import com.telegame.code.repos.PlayerPlayMatchRepo;
import com.telegame.code.repos.PlayerRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class MatchService {
    private ValidatorFactory validatorFactory;
    private PlayerRepo playerRepo;
    private GameMatchRepo gameMatchRepo;
    private PlayerPlayMatchRepo playerPlayMatchRepo;

    public String createMatch(MatchForm matchForm, String playerName) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<MatchForm>> formErrorList = validatorFactory.getValidator().validate(matchForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();

        Optional<Player> playerOneCandidate = playerRepo.findByPlayerNameEquals(playerName);
        if (playerOneCandidate.isEmpty()) throw new PlayerNameException();
        if (!matchForm.getIsPublic() && matchForm.getPassword() != null) throw new InputFormException();

        PlayerPlayMatch playerOnePlayMatch = PlayerPlayMatch.builder()
                .player(playerOneCandidate.get())
                .matchCreation(LocalDateTime.now())
                .build();

        GameMatch newGameMatch = GameMatch.builder()
                .name(matchForm.getMatchName())
                .isPublic(matchForm.getIsPublic())
                .password(matchForm.getIsPublic() ? null : HashUtils.getHashSHA256(matchForm.getPassword()))
                .player(playerOnePlayMatch)
                .board(getBoard(matchForm.getGame()))
                .build();

        playerOnePlayMatch.setGameMatch(newGameMatch);

        gameMatchRepo.save(newGameMatch);
        playerPlayMatchRepo.save(playerOnePlayMatch);

        return "Ok";

    }

    private Board getBoard(String game) {
        return switch (game) {
            case "LASER_BOARD" -> null;
            case "TIC_TAC_TOE" -> null;
            default -> throw new GameNoExistsException();
        };
    }
}

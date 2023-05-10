package com.telegame.code.services;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.exceptions.*;
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
import java.util.List;
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
                .lastUpdate(LocalDateTime.now())
                .build();

        GameMatch newGameMatch = GameMatch.builder()
                .name(matchForm.getMatchName())
                .isPublic(matchForm.getIsPublic())
                .password(matchForm.getIsPublic() ? null : HashUtils.getHashSHA256(matchForm.getPassword()))
                .player(playerOnePlayMatch)
                .board(getBoard(matchForm.getGame()))
                .matchCreation(LocalDateTime.now())
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

    public String joinMatch(Long matchId, String playerName) {
        Optional<GameMatch> gameMatchCandidate = gameMatchRepo.findById(matchId);
        if (gameMatchCandidate.isEmpty()) throw new MatchNoExistsException();
        GameMatch gameMatch = checkGameMatch(gameMatchCandidate.get());

        Optional<Player> playerOneCandidate = playerRepo.findByPlayerNameEquals(playerName);
        if (playerOneCandidate.isEmpty()) throw new PlayerNameException();
        Player player = checkPlayer(playerOneCandidate.get(), gameMatch);

        PlayerPlayMatch playerPlayMatch = PlayerPlayMatch.builder()
                .gameMatch(gameMatch)
                .player(player)
                .lastUpdate(LocalDateTime.now())
                .build();

        gameMatch.addPlayer(playerPlayMatch);

        return "ok";
    }

    private Player checkPlayer(Player playerTwo, GameMatch gameMatch) {
        Player playerOne = gameMatch.getPlayers().get(0).getPlayer();
        if (playerOne.getPlayerName().equals(playerTwo.getPlayerName())) throw new PlayerAlreadyInMatchException();
        return playerTwo;
    }

    private GameMatch checkGameMatch(GameMatch gameMatch) {
        int size = gameMatch.getPlayers().size();
        if (size > 1) throw new FilledMatchException();
        if (size == 0) {
            gameMatchRepo.delete(gameMatch);
            throw new MatchNoExistsException();
        }
        return gameMatch;
    }


}

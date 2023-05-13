package com.telegame.code.services;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.exceptions.GameNoExistsException;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.match.*;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.JoinMatchForm;
import com.telegame.code.forms.MatchForm;
import com.telegame.code.models.Board;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.GameMatchRepo;
import com.telegame.code.repos.PlayerPlayMatchRepo;
import com.telegame.code.repos.PlayerRepo;
import com.telegame.code.services.games.KingOLaserService;
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
    private BoardRepo boardRepo;
    private KingOLaserService kingOLaserService;

    public String createMatch(MatchForm matchForm, String playerName) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<MatchForm>> formErrorList = validatorFactory.getValidator().validate(matchForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();
        if (privacyInconsistency(matchForm)) throw new InputFormException();

        Optional<Player> playerOneCandidate = playerRepo.findByPlayerNameEquals(playerName);
        if (playerOneCandidate.isEmpty()) throw new PlayerNameException();
        Player playerOne = playerOneCandidate.get();

        PlayerPlayMatch playerOnePlayMatch = PlayerPlayMatch.builder()
                .player(playerOne)
                .lastUpdate(LocalDateTime.now())
                .build();

        GameMatch newGameMatch = buildGameMatch(matchForm, playerOnePlayMatch);
        playerOnePlayMatch.setGameMatch(newGameMatch);

        boardRepo.save(getBoard(newGameMatch, matchForm.getGame(), matchForm.getMetadata()));
        playerPlayMatchRepo.save(playerOnePlayMatch);

        return "Ok";
    }

    private boolean privacyInconsistency(MatchForm matchForm) {
        return (!matchForm.getIsPublic() && matchForm.getPassword() == null) || (matchForm.getIsPublic() && matchForm.getPassword() != null);
    }

    private GameMatch buildGameMatch(MatchForm matchForm, PlayerPlayMatch playerOnePlayMatch) throws NoSuchAlgorithmException {
        return GameMatch.builder()
                .name(matchForm.getMatchName())
                .isPublic(matchForm.getIsPublic())
                .password(matchForm.getIsPublic() ? null : HashUtils.getHashSHA256(matchForm.getPassword()))
                .player(playerOnePlayMatch)
                .matchCreation(LocalDateTime.now())
                .build();
    }

    private Board getBoard(GameMatch newGameMatch, String game, String metadata) {
        return switch (game) {
            case "LASER_BOARD" -> kingOLaserService.generateBoard(newGameMatch, metadata);
            case "TIC_TAC_TOE" -> null;
            default -> throw new GameNoExistsException();
        };
    }

    public String joinMatch(Long matchId, JoinMatchForm joinMatchForm, String playerName) throws NoSuchAlgorithmException {
        Optional<GameMatch> gameMatchCandidate = gameMatchRepo.findById(matchId);
        if (gameMatchCandidate.isEmpty()) throw new MatchNoExistsException();
        GameMatch gameMatch = checkGameMatch(gameMatchCandidate.get(), joinMatchForm);

        Optional<Player> playerOneCandidate = playerRepo.findByPlayerNameEquals(playerName);
        if (playerOneCandidate.isEmpty()) throw new PlayerNameException();
        Player player = checkPlayer(playerOneCandidate.get(), gameMatch);

        PlayerPlayMatch playerPlayMatch = PlayerPlayMatch.builder()
                .gameMatch(gameMatch)
                .player(player)
                .lastUpdate(LocalDateTime.now())
                .build();

        playerPlayMatchRepo.save(playerPlayMatch);
        setBoardStatusReady(gameMatch);

        return "ok";
    }

    private GameMatch checkGameMatch(GameMatch gameMatch, JoinMatchForm joinMatchForm) throws NoSuchAlgorithmException {
        if (!gameMatch.getIsPublic()) checkPassword(joinMatchForm, gameMatch.getPassword());
        if (gameMatch.getPlayers().size() != 1) throw new FilledMatchException();
        return gameMatch;
    }

    private void checkPassword(JoinMatchForm joinMatchForm, String password) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<JoinMatchForm>> formErrorList = validatorFactory.getValidator().validate(joinMatchForm);
        if (!formErrorList.isEmpty()) throw new MatchInfoException();
        if ((joinMatchForm.getPassword() == null) || (!HashUtils.getHashSHA256(joinMatchForm.getPassword()).equals(password))) {
            throw new MatchInfoException();
        }
    }

    private Player checkPlayer(Player playerTwo, GameMatch gameMatch) {
        Player playerOne = gameMatch.getPlayers().get(0).getPlayer();
        if (playerOne.getPlayerName().equals(playerTwo.getPlayerName())) throw new PlayerAlreadyInMatchException();
        return playerTwo;
    }

    private void setBoardStatusReady(GameMatch gameMatch) {
        Optional<Board> boardOptional = boardRepo.findBoardByGameMatch(gameMatch);
        if (boardOptional.isEmpty()) throw new BoardNoExistsException();
        Board board = boardOptional.get();

        if (Math.random() < 0.5) board.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
        else board.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);

        boardRepo.save(board);
    }
}

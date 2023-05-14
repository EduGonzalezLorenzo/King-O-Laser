package com.telegame.code.services;

import com.telegame.code.DTO.GameMatchDTO;
import com.telegame.code.DTO.games.kingolaser.LaserBoardDTO;
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
import com.telegame.code.models.games.kingolaser.LaserBoard;
import com.telegame.code.repos.BoardRepo;
import com.telegame.code.repos.GameMatchRepo;
import com.telegame.code.repos.PlayerPlayMatchRepo;
import com.telegame.code.repos.PlayerRepo;
import com.telegame.code.services.games.LaserBoardService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private BoardRepo boardRepo;
    private LaserBoardService laserBoardService;

    public String createMatch(MatchForm matchForm, String playerName) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<MatchForm>> formErrorList = validatorFactory.getValidator().validate(matchForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();
        if (privacyInconsistency(matchForm)) throw new InputFormException();

        Player playerOne = getPlayer(playerName);

        PlayerPlayMatch playerOnePlayMatch = PlayerPlayMatch.builder()
                .player(playerOne)
                .lastUpdate(LocalDateTime.now())
                .build();

        GameMatch newGameMatch = buildGameMatch(matchForm, playerOnePlayMatch);
        playerOnePlayMatch.setGameMatch(newGameMatch);

        Board board = createBoard(newGameMatch, matchForm.getGame(), matchForm.getMetadata());
        boardRepo.save(board);
        saveBoardComponents(matchForm.getGame(), board);

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

    private Board createBoard(GameMatch newGameMatch, String game, String metadata) {
        return switch (game) {
            case "LASER_BOARD" -> laserBoardService.generateBoard(newGameMatch, metadata);
            case "TIC_TAC_TOE" -> null;
            default -> throw new GameNoExistsException();
        };
    }

    private void saveBoardComponents(String game, Board board) {
        switch (game) {
            case "LASER_BOARD" -> laserBoardService.savePieces((LaserBoard) board);
            case "TIC_TAC_TOE" -> System.out.println();
            default -> throw new GameNoExistsException();
        }
    }

    public String joinMatch(Long matchId, JoinMatchForm joinMatchForm, String playerName) throws NoSuchAlgorithmException {
        GameMatch gameMatch = checkGameMatch(getGameMatch(matchId), joinMatchForm);

        Player player = checkPlayer(getPlayer(playerName), gameMatch);

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
        //TODO check empty match case
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
        Board board = getBoard(gameMatch);

        if (Math.random() < 0.5) board.setStatus(Board.MatchStatus.PLAYER_ONE_TURN);
        else board.setStatus(Board.MatchStatus.PLAYER_TWO_TURN);

        boardRepo.save(board);
    }


    public List<GameMatchDTO> getPlayerCurrentMatches(String playerName) {
        Player player = getPlayer(playerName);

        List<PlayerPlayMatch> playerPlayMatchList = playerPlayMatchRepo.findByPlayerEquals(player);

        return generateGameMatchDTOsList(playerPlayMatchList);
    }

    private List<GameMatchDTO> generateGameMatchDTOsList(List<PlayerPlayMatch> playerPlayMatchList) {
        List<GameMatchDTO> gameMatchDTOList = new ArrayList<>();
        for (PlayerPlayMatch playerPlayMatch : playerPlayMatchList) {
            gameMatchDTOList.add(generateGameMatchDTO(playerPlayMatch.getGameMatch()));
        }
        return gameMatchDTOList;
    }

    private GameMatchDTO generateGameMatchDTO(GameMatch gameMatch) {
        Board board = getBoard(gameMatch);
        return GameMatchDTO.builder()
                .id(gameMatch.getId())
                .isPublic(gameMatch.getIsPublic())
                .matchCreation(gameMatch.getMatchCreation())
                .name(gameMatch.getName())
                .currentPlayers(gameMatch.getPlayers().size())
                .status(board.getStatus().toString())
                .build();
    }

    public LaserBoardDTO getMatchInfo(Long matchId, String playerName) {
        GameMatch gameMatch = getGameMatch(matchId);
        Player player = getPlayer(playerName);
        checkPlayerInMatch(player, gameMatch);

        return generateBoardDTO(getBoard(gameMatch));
    }

    private void checkPlayerInMatch(Player player, GameMatch gameMatch) {
        if (playerPlayMatchRepo.findByPlayerEqualsAndGameMatchEquals(player, gameMatch).isEmpty()) {
            throw new PlayerNoInMatchException();
        }
    }

    private LaserBoardDTO generateBoardDTO(Board board) {
        if (board instanceof LaserBoard) return laserBoardService.generateLaserBoardDTO((LaserBoard) board);
        else return null;
    }

    private Player getPlayer(String playerName) {
        Optional<Player> playerOptional = playerRepo.findByPlayerNameEquals(playerName);
        if (playerOptional.isEmpty()) throw new PlayerNameException();
        return playerOptional.get();
    }

    private GameMatch getGameMatch(Long matchId) {
        Optional<GameMatch> gameMatchCandidate = gameMatchRepo.findById(matchId);
        if (gameMatchCandidate.isEmpty()) throw new MatchNoExistsException();
        return gameMatchCandidate.get();
    }

    private Board getBoard(GameMatch gameMatch) {
        Optional<Board> boardOptional = boardRepo.findBoardByGameMatch(gameMatch);
        if (boardOptional.isEmpty()) throw new BoardNoExistsException();
        return boardOptional.get();
    }

    public List<GameMatchDTO> getAvailableMatches() {
        List<PlayerPlayMatch> matchList = playerPlayMatchRepo.findAll();

        return generateGameMatchDTOsList(matchList);
    }

}
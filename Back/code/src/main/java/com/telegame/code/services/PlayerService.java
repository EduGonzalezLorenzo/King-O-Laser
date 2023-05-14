package com.telegame.code.services;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.builder.PlayerBuilder;
import com.telegame.code.exceptions.player.EmailException;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.player.LoginException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.LoginForm;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.Player;
import com.telegame.code.repos.PlayerRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerService {

    private ValidatorFactory validatorFactory;
    private PlayerRepo playerRepo;
    private TokenService tokenService;

    public String signUp(PlayerForm playerForm) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<PlayerForm>> formErrorList = validatorFactory.getValidator().validate(playerForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();

        if (playerRepo.findByEmailEquals(playerForm.getEmail()).isPresent()) throw new EmailException();
        if (playerRepo.findByPlayerNameEquals(playerForm.getPlayerName()).isPresent()) throw new PlayerNameException();

        playerRepo.save(PlayerBuilder.fromForm(playerForm));
        return "ok";
    }


    public String login(LoginForm loginForm) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<LoginForm>> formErrorList = validatorFactory.getValidator().validate(loginForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();

        if (loginForm.getPlayerName() != null && loginForm.getEmail() != null) throw new LoginException();
        Player player = getPlayer(loginForm);
        if (wrongNameEmailOrPassword(player, loginForm)) throw new LoginException();

        return tokenService.createUserToken(player);
    }

    private Player getPlayer(LoginForm loginForm) {

        return loginForm.getPlayerName() == null ?
                getPlayerByEmail(loginForm.getEmail()) :
                getPlayerByName(loginForm.getPlayerName());
    }

    private boolean wrongNameEmailOrPassword(Player player, LoginForm loginForm) throws NoSuchAlgorithmException {
        return player == null || !player.getPassword().equals(HashUtils.getHashSHA256(loginForm.getPassword()));
    }

    private Player getPlayerByName(String playerName) {
        Optional<Player> player = playerRepo.findByPlayerNameEquals(playerName);
        return player.orElse(null);
    }

    private Player getPlayerByEmail(String email) {
        Optional<Player> player = playerRepo.findByEmailEquals(email);
        return player.orElse(null);
    }

    public Map<String, String> updatePlayerInfo(PlayerForm playerForm, Long id, Object candidate) {
        return new HashMap<>();
    }

    public Map<String, String> deletePlayerInfo(Long playerId, Object candidate) {
        return new HashMap<>();
    }

    public String  getPlayerInfo(String playerName) {
        return playerName;
    }
}

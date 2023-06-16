package com.telegame.code.services;

import com.telegame.code.DTO.PlayerDTO;
import com.telegame.code.Utils.HashUtils;
import com.telegame.code.builder.PlayerBuilder;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.player.*;
import com.telegame.code.forms.LoginForm;
import com.telegame.code.forms.SignUpForm;
import com.telegame.code.forms.UpdatePlayerForm;
import com.telegame.code.models.Player;
import com.telegame.code.repos.PlayerRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerService {

    private ValidatorFactory validatorFactory;
    private PlayerRepo playerRepo;
    private TokenService tokenService;

    public String signUp(SignUpForm signUpForm) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<SignUpForm>> formErrorList = validatorFactory.getValidator().validate(signUpForm);
        if (!formErrorList.isEmpty()) throw new InputFormException();
        if (playerRepo.findByEmailEquals(signUpForm.getEmail()).isPresent()) throw new EmailException();
        if (playerNameAlreadyRegistered(signUpForm.getPlayerName())) throw new PlayerNameException();

        playerRepo.save(PlayerBuilder.fromForm(signUpForm));
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
        if (player == null) return true;
        if (player.getPassword() == null) throw new GoogleException();
        return !player.getPassword().equals(HashUtils.getHashSHA256(loginForm.getPassword()));
    }

    private Player getPlayerByName(String playerName) {
        Optional<Player> player = playerRepo.findByPlayerNameEquals(playerName);
        return player.orElse(null);
    }

    private Player getPlayerByEmail(String email) {
        Optional<Player> player = playerRepo.findByEmailEquals(email);
        return player.orElse(null);
    }

    public PlayerDTO getPlayerInfo(String playerName) {
        return PlayerDTO.builder()
                .playerName(playerName)
                .profileImgUrl("")
                .loggedIn(true)
                .build();
    }

    public String updatePlayerInfo(UpdatePlayerForm updatePlayerForm, String playerName) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<UpdatePlayerForm>> formErrorList = validatorFactory.getValidator().validate(updatePlayerForm);
        if (!formErrorList.isEmpty() || formValuesNotValid(updatePlayerForm)) throw new InputFormException();
        if (playerNameAlreadyRegistered(updatePlayerForm.getPlayerName())) throw new PlayerNameAlreadyExistsException();

        Player oldPlayer = getPlayerByName(playerName);
        if (oldPlayer == null) throw new PlayerNameException();


        Player updatedPlayer = generateUpdatedPlayer(oldPlayer, updatePlayerForm);
        playerRepo.save(updatedPlayer);
        return "ok";
    }

    private boolean playerNameAlreadyRegistered(String playerName) {
        return playerRepo.findByPlayerNameEquals(playerName).isPresent();
    }

    private boolean formValuesNotValid(UpdatePlayerForm updatePlayerForm) {
        return ((updatePlayerForm.getPlayerName().length() > 0 && updatePlayerForm.getPlayerName().length() < 3)
                || (updatePlayerForm.getPassword().length() > 0 && updatePlayerForm.getPassword().length() < 8)
                || (updatePlayerForm.getFirstName().length() > 0 && updatePlayerForm.getFirstName().length() < 3)
                || (updatePlayerForm.getLastName().length() > 0 && updatePlayerForm.getLastName().length() < 3));
    }

    private Player generateUpdatedPlayer(Player oldPlayer, UpdatePlayerForm updatePlayerForm) throws NoSuchAlgorithmException {
        return Player.builder()
                .id(oldPlayer.getId())
                .email(oldPlayer.getEmail())
                .playerName(updatePlayerForm.getPlayerName().equals("") ? oldPlayer.getPlayerName() : updatePlayerForm.getPlayerName())
                .firstName(updatePlayerForm.getFirstName().equals("") ? oldPlayer.getFirstName() : updatePlayerForm.getFirstName())
                .lastName(updatePlayerForm.getLastName().equals("") ? oldPlayer.getLastName() : updatePlayerForm.getLastName())
                .password(updatePlayerForm.getPassword().equals("") ? oldPlayer.getPassword() : HashUtils.getHashSHA256(updatePlayerForm.getPassword()))
                .build();
    }
}

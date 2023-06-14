package com.telegame.code.services;

import com.telegame.code.DTO.Message;
import com.telegame.code.DTO.PlayerDTO;
import com.telegame.code.Utils.HashUtils;
import com.telegame.code.builder.PlayerBuilder;
import com.telegame.code.exceptions.InputFormException;
import com.telegame.code.exceptions.player.EmailException;
import com.telegame.code.exceptions.player.GoogleException;
import com.telegame.code.exceptions.player.LoginException;
import com.telegame.code.exceptions.player.PlayerNameException;
import com.telegame.code.forms.LoginForm;
import com.telegame.code.forms.SignUpForm;
import com.telegame.code.forms.UpdatePlayerForm;
import com.telegame.code.models.Player;
import com.telegame.code.repos.PlayerRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        if (playerRepo.findByPlayerNameEquals(signUpForm.getPlayerName()).isPresent()) throw new PlayerNameException();

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
        if (!formErrorList.isEmpty()) throw new InputFormException();

        Player oldPlayer = getPlayerByName(playerName);
        Player updatedPlayer = generateUpdatedPlayer(oldPlayer, updatePlayerForm);

        playerRepo.save(updatedPlayer);
        return "ok";
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

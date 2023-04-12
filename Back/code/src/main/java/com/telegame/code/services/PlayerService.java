package com.telegame.code.services;

import com.telegame.code.builder.PlayerBuilder;
import com.telegame.code.exceptions.EmailException;
import com.telegame.code.exceptions.InputPlayerFormException;
import com.telegame.code.exceptions.PlayerNameException;
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
import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerService {

    private ValidatorFactory validatorFactory;
    private PlayerRepo playerRepo;

    public String signUp(PlayerForm playerForm) throws NoSuchAlgorithmException {
        Set<ConstraintViolation<PlayerForm>> formErrorList = validatorFactory.getValidator().validate(playerForm);
        if (!formErrorList.isEmpty()) throw new InputPlayerFormException();
        if (playerRepo.findByEmailEquals(playerForm.getEmail()).isPresent()) throw new EmailException();
        if (playerRepo.findByPlayerNameEquals(playerForm.getPlayerName()).isPresent()) throw new PlayerNameException();

        playerRepo.save(PlayerBuilder.fromForm(playerForm));
        return "ok";
    }


    public Player login(PlayerForm playerForm) {
        return null;
    }

    public Map<String, String> updatePlayerInfo(PlayerForm playerForm, Long id, Object candidate) {
        Map<String, String> result = new HashMap<>();
        return result;
    }

    public Map<String, String> deletePlayerInfo(Long playerId, Object candidate) {
        Map<String, String> result = new HashMap<>();
        return result;
    }

    public Player getPlayerInfo(Object candidate) {
        return null;
    }
}

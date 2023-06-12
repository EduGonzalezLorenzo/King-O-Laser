package com.telegame.code.builder;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.Player;

import java.security.NoSuchAlgorithmException;

public class PlayerBuilder {
    public static Player fromForm(PlayerForm playerForm) throws NoSuchAlgorithmException {
        return Player.builder()
                .playerName(playerForm.getPlayerName())
                .email(playerForm.getEmail())
                .firstName(playerForm.getFirstName())
                .lastName(playerForm.getLastName())
                .password(playerForm.getPassword() == null ? null : HashUtils.getHashSHA256(playerForm.getPassword()))
                .build();
    }

}

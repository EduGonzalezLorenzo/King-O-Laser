package com.telegame.code.builder;

import com.auth0.jwt.interfaces.Claim;
import com.telegame.code.DTO.PlayerDTO;
import com.telegame.code.Utils.HashUtils;
import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.GameMatch;
import com.telegame.code.models.Player;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class PlayerBuilder {
    public static Player fromForm(PlayerForm playerForm) throws NoSuchAlgorithmException {
        return Player.builder()
                .playerName(playerForm.getPlayerName())
                .email(playerForm.getEmail())
                .firstName(playerForm.getFirstName())
                .lastName(playerForm.getLastName())
                .password(HashUtils.getHashSHA256(playerForm.getPassword()))
                .build();
    }

    public static PlayerDTO fromJWT(String userName, long iat) {
        return PlayerDTO.builder()
                .userName(userName)
                .build();
    }
}

package com.telegame.code.builder;

import com.telegame.code.Utils.HashUtils;
import com.telegame.code.forms.SignUpForm;
import com.telegame.code.models.Player;

import java.security.NoSuchAlgorithmException;

public class PlayerBuilder {
    public static Player fromForm(SignUpForm signUpForm) throws NoSuchAlgorithmException {
        return Player.builder()
                .playerName(signUpForm.getPlayerName())
                .email(signUpForm.getEmail())
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .password(signUpForm.getPassword() ==  null ? null : HashUtils.getHashSHA256(signUpForm.getPassword()))
                .build();
    }

}

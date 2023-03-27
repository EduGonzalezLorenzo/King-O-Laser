package com.kingolaser.code.builder;

import com.kingolaser.code.Utils.HashUtils;
import com.kingolaser.code.forms.PlayerForm;
import com.kingolaser.code.models.Player;

import java.security.NoSuchAlgorithmException;

public class PlayerBuilder {
    public static Player fromForm(PlayerForm playerForm) throws NoSuchAlgorithmException {
        Player player = new Player();

        player.setPlayerName(playerForm.getPlayerName());
        player.setEmail(playerForm.getEmail());
        player.setName(playerForm.getName());
        player.setPassword(HashUtils.getHashSHA256(playerForm.getPassword()));

        return player;
    }
}

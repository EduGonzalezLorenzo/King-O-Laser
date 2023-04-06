package com.telegame.code.services;

import com.telegame.code.forms.PlayerForm;
import com.telegame.code.models.Player;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlayerService {
    public Map<String, String> signUp(PlayerForm playerForm) {
        Map<String, String> result = new HashMap<>();
        return result;
    }

    public Player login(PlayerForm playerForm) {
        Player player = new Player();
        return player;
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
        Player player = new Player();
        return player;
    }
}

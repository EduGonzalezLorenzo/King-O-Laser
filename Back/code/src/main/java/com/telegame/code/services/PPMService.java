package com.telegame.code.services;

import com.telegame.code.models.Player;
import com.telegame.code.models.PlayerPlayMatch;
import com.telegame.code.repos.PlayerPlayMatchRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PPMService {

    private PlayerPlayMatchRepo ppmRepo;

    public PlayerPlayMatch createPpm(Player playerOne) {
//        PlayerPlayMatch player_play_match = new PlayerPlayMatch();
//        player_play_match.setPlayer(playerOne);
//        player_play_match.setMatchCreation(LocalDateTime.now());
//        ppmRepo.save(player_play_match);
//        return player_play_match;
        return null;
    }

    public void savePPM(PlayerPlayMatch ppm) {
        ppmRepo.save(ppm);
    }
}

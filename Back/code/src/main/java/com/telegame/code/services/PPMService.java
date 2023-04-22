package com.telegame.code.services;

import com.telegame.code.models.Player;
import com.telegame.code.models.Player_Play_Match;
import com.telegame.code.repos.PPMRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PPMService {

    private PPMRepo ppmRepo;
    public Player_Play_Match createPpm(Player playerOne) {
        Player_Play_Match player_play_match = new Player_Play_Match();
        player_play_match.setPlayer(playerOne);
        player_play_match.setMatchCreation(LocalDateTime.now());
        ppmRepo.save(player_play_match);
        return player_play_match;
    }

    public void savePPM(Player_Play_Match ppm) {
        ppmRepo.save(ppm);
    }
}

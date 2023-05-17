package com.telegame.code.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPlayMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime lastUpdate;
    String position;
    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player;

    @ManyToOne
    @JoinColumn(name = "game_match_id")
    GameMatch gameMatch;
}

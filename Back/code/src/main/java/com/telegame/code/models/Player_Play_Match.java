package com.telegame.code.models;

import com.telegame.code.models.kingolaser.pieces.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player_Play_Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player;

    Piece.Owner playerNumber;

    @ManyToOne
    @JoinColumn(name = "game_match_id")
    GameMatch gameMatch;

    LocalDateTime matchCreation;
}

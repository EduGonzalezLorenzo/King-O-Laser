package com.telegame.code.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Board {

    public enum MatchStatus {
        WAITING, PLAYER_ONE_TURN, PLAYER_TWO_TURN, PLAYER_ONE_WIN, PLAYER_TWO_WIN, DRAFT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    GameMatch gameMatch;

    @Enumerated(EnumType.STRING)
    MatchStatus status;
}

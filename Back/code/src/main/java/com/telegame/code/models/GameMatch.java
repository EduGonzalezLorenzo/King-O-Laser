package com.telegame.code.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class GameMatch {
    public enum MatchStatus {
        WAITING, PLAYER_ONE_TURN, PLAYER_TWO_TURN, PLAYER_ONE_WIN, PLAYER_TWO_WIN, DRAFT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String password;
    Boolean isPublic;
    @Enumerated(EnumType.STRING)
    MatchStatus status;

    @OneToMany(mappedBy = "gameMatch")
    List<Player_Play_Match> players;
    @OneToOne(mappedBy = "gameMatch")
    Board board;
}
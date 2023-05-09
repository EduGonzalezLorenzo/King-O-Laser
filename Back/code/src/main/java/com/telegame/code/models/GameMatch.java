package com.telegame.code.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class GameMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String password;
    Boolean isPublic;
    LocalDateTime matchCreation;

    @OneToMany(mappedBy = "gameMatch")
    @Singular
    List<PlayerPlayMatch> players;
    @OneToOne(mappedBy = "gameMatch", cascade = CascadeType.REMOVE)
    Board board;

    public void addPlayer(PlayerPlayMatch newPlayer) {
        this.players.add(newPlayer);
    }
}
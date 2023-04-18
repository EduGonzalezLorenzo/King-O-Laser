package com.telegame.code.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class GameMatch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String password;
    Boolean isPublic;

    @OneToMany(mappedBy = "gameMatch")
    List<Player_Play_Match> players;
    @OneToOne(mappedBy = "gameMatch")
    Board board;
}
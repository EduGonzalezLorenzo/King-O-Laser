package com.telegame.code.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String playerName;
    @NotNull
    String password;
    @NotNull
    String email;
    String firstName;
    String lastName;

    @OneToMany(mappedBy = "player")
    List<Player_Play_Match> matches;
}

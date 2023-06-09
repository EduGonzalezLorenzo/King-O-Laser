package com.telegame.code.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String playerName;
    String password;
    @NotNull
    String email;
    String firstName;
    String lastName;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE)
    List<PlayerPlayMatch> matches;
}

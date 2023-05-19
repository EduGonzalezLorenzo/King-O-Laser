package com.telegame.code.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class GameMatchDTO {
    Long id;
    String name;
    Boolean isPublic;
    LocalDateTime matchCreation;
    int currentPlayers;
    String status;
    String position;
}

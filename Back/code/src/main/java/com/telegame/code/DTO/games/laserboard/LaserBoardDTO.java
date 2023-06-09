package com.telegame.code.DTO.games.laserboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder
public class LaserBoardDTO {
    String lastAction;
    String status;
    String position;
    long matchId;

    @Singular
    List<PieceDTO> pieces;
}

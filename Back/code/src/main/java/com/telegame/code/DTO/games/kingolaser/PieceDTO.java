package com.telegame.code.DTO.games.kingolaser;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PieceDTO {
    int x;
    int y;
    String owner;
    String type;
    String rotation;
}

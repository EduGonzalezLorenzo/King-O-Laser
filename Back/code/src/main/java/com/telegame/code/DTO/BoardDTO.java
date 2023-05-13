package com.telegame.code.DTO;

import com.telegame.code.models.games.kingolaser.pieces.Piece;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoardDTO {
    long id;
    List<Piece> pieceList;
}

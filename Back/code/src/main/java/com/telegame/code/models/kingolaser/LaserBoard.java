package com.telegame.code.models.kingolaser;

import com.telegame.code.models.Board;
import com.telegame.code.models.kingolaser.pieces.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaserBoard implements Board {
    List<Piece> pieceList;
    LaserBeam laserBeam;
}

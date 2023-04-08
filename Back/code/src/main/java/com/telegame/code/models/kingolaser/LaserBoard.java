package com.telegame.code.models.kingolaser;

import com.telegame.code.models.Board;
import com.telegame.code.models.kingolaser.pieces.Piece;

import java.util.List;

public class LaserBoard implements Board {
    List<Piece> pieceList;
    LaserBeam laserBeam;
}

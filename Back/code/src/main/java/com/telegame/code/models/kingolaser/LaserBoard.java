package com.telegame.code.models.kingolaser;

import com.telegame.code.models.kingolaser.pieces.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LaserBoard {

    List<Piece> pieceList;
    LaserBeam laserBeam;

//    public LaserBoard(List<Piece> piecesList, LaserBeam laserBeam) {
//        this.pieceList = piecesList;
//        this.laserBeam = laserBeam;
//    }
}

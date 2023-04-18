package com.telegame.code.models.kingolaser;

import com.telegame.code.models.Board;
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
@Entity
public class LaserBoard extends Board {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;

    @OneToMany(mappedBy = "laserBoard")
    List<Piece> pieceList;
}

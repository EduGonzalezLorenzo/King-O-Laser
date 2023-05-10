package com.telegame.code.models.games.kingolaser;

import com.telegame.code.models.Board;
import com.telegame.code.models.games.kingolaser.pieces.Piece;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class LaserBoard extends Board {
    @OneToMany(mappedBy = "laserBoard")
    List<Piece> pieceList;
}

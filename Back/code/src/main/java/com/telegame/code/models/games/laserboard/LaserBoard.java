package com.telegame.code.models.games.laserboard;

import com.telegame.code.models.Board;
import com.telegame.code.models.games.laserboard.pieces.Piece;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LaserBoard extends Board {
    @OneToMany(mappedBy = "laserBoard")
    List<Piece> pieceList;
}

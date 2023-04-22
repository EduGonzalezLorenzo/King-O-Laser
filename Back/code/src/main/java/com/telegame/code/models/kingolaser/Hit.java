package com.telegame.code.models.kingolaser;

import com.telegame.code.models.kingolaser.pieces.Piece;
import com.telegame.code.models.kingolaser.pieces.PieceSide;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Hit implements PieceSide {

    @Override
    public Piece.Direction interact(Piece.Direction direction, Piece.Direction rotation, boolean bouncer) {
        System.out.println("La pieza ha muerto");
        return null;
    }
}

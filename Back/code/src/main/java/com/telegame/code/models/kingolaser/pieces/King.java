package com.telegame.code.models.kingolaser.pieces;

import jakarta.persistence.Entity;

@Entity
public class King extends Piece {
    @Override
    String laserInteraction(String laserSource) {
        return null;
    }
}

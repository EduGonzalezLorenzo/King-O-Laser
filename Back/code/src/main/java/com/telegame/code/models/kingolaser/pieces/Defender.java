package com.telegame.code.models.kingolaser.pieces;

import jakarta.persistence.Entity;

@Entity
public class Defender extends Piece {

    @Override
    String laserInteraction(String laserSource) {
        return null;
    }
}
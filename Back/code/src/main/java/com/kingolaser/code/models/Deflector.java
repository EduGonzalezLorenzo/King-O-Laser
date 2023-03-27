package com.kingolaser.code.models;

import jakarta.persistence.Entity;

@Entity
public class Deflector extends Piece{
    @Override
    String laserInteraction(String laserSource) {
        return null;
    }
}

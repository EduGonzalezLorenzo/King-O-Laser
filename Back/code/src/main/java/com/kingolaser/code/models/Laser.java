package com.kingolaser.code.models;

import jakarta.persistence.Entity;

@Entity
public class Laser extends Piece{
    @Override
    String laserInteraction(String laserSource) {
        return null;
    }
}

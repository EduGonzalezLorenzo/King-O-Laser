package com.kingolaser.code.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int x;
    int y;
    String orientation;

    abstract String laserInteraction(String laserSource);
}
package com.kingolaser.code.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Player playerOne;
    Player playerTwo;
    List<Piece> pieceList;
    Boolean isPublic;
    Boolean isOnline;
}
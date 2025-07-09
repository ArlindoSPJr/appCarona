package com.carona.AppDeCarona.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "corrida")
public class Corrida {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long corridaId;

    private String localDeOrigem;
    private String localDeDestino;

    
}

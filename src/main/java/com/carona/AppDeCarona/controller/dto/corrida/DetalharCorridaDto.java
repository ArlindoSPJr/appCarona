package com.carona.AppDeCarona.controller.dto.corrida;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.carona.AppDeCarona.entity.Corrida;

public record DetalharCorridaDto(String localDeOrigem,
                                String localDeDestino,
                                String motorista,
                                LocalTime horarioDeSaida,
                                LocalDateTime previsaoDeChegada,
                                LocalDate dataDeSaida,
                                double duracao) {

    public DetalharCorridaDto(Corrida corrida){
        this(corrida.getLocalDeOrigem(), corrida.getLocalDeDestino(), 
        corrida.getMotorista().getNome(), corrida.getHorarioDeSaida(), 
        corrida.getPrevisaoDeChegada(),corrida.getDataDeSaida(), corrida.getDuracao());
    }
}

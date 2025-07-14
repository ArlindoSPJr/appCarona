package com.carona.AppDeCarona.controller.dto.corrida;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateCorridaDto(String localDeOrigem,
                               String localDeDestino,
                               String motorista,
                               LocalTime horarioDeSaida,
                               LocalDate dataDeSaida,
                               int capacidade ) {

}

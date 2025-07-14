package com.carona.AppDeCarona.controller.dto.corrida;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.carona.AppDeCarona.entity.Corrida;
import com.carona.AppDeCarona.entity.Usuario;

public record DetalharCorridaDto(
        int capacidade,
        String localDeOrigem,
        String localDeDestino,
        String motorista,
        LocalTime horarioDeSaida,
        LocalDateTime previsaoDeChegada,
        LocalDate dataDeSaida,
        String duracao, // Agora vem formatado como HH:mm
        List<Usuario> passageiros
) {

    public DetalharCorridaDto(Corrida corrida) {
        this(
            corrida.getCapacidade(),
            corrida.getLocalDeOrigem(),
            corrida.getLocalDeDestino(),
            corrida.getMotorista().getNome(),
            corrida.getHorarioDeSaida(),
            corrida.getPrevisaoDeChegada(),
            corrida.getDataDeSaida(),
            formatarDuracao(corrida.getDuracao()),
            corrida.getPassageiros()
        );
    }

    private static String formatarDuracao(double duracaoHoras) {
        int totalMinutes = (int) Math.round(duracaoHoras * 60);
        int horas = totalMinutes / 60;
        int minutos = totalMinutes % 60;
        return String.format("%02d:%02d", horas, minutos);
    }
}

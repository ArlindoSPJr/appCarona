package com.carona.AppDeCarona.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carona.AppDeCarona.controller.dto.corrida.CreateCorridaDto;
import com.carona.AppDeCarona.controller.dto.corrida.DetalharCorridaDto;
import com.carona.AppDeCarona.entity.Corrida;
import com.carona.AppDeCarona.entity.enums.StatusCorrida;
import com.carona.AppDeCarona.repository.CorridaRepository;
import com.carona.AppDeCarona.repository.UsuarioRepository;

@Service
public class CorridaService {

    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OpenRouteService openRouteService;

    public Corrida criarCorrida(CreateCorridaDto dto) {

        var motorista = usuarioRepository.findByNome(dto.motorista());
        if (motorista == null) {
            throw new UsernameNotFoundException("Nome de usuário não encontrado!");
        }

        var distanciaEDuracao = openRouteService.calcularDistanciaPorEndereco(dto.localDeOrigem(), dto.localDeDestino())
                .block();

        Corrida corrida = new Corrida(dto, motorista);
        double duracaoSegundos = distanciaEDuracao.getDuracao();
        double duracaoHoras = duracaoSegundos / 3600.0;
        corrida.setDuracao(duracaoHoras);
        var previsaoDeChegada = dto.dataDeSaida().atTime(dto.horarioDeSaida())
                .plusMinutes((long) (duracaoHoras * 60));

        corrida.setPrevisaoDeChegada(previsaoDeChegada);
        corrida.setStatusCorrida(StatusCorrida.CONFIRMADA);
        corridaRepository.save(corrida);

        return corrida;

    }

    public List<DetalharCorridaDto> listarCorridas() {
        return corridaRepository.findAll()
                .stream()
                .map(DetalharCorridaDto::new)
                .toList();
    }
}
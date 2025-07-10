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

import jakarta.persistence.EntityNotFoundException;

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

    public void iniciarCorrida(Long corridaId) {
        var corrida = buscarCorridaPeloId(corridaId);
        corrida.setStatusCorrida(StatusCorrida.INICIADA);
        corridaRepository.save(corrida);
    }

    public void cancelarCorrida(Long corridaId) {
        var corrida = buscarCorridaPeloId(corridaId);
        if (!corrida.getStatusCorrida().equals(StatusCorrida.CONFIRMADA)) {
            throw new IllegalStateException("Não é possivel cancelar uma corrida que ja foi iniciada ou finalizada!");
        }
        corrida.setStatusCorrida(StatusCorrida.CANCELADA);
        corridaRepository.save(corrida);
    }

    public void finalizarCorrida(Long corridaId){
        var corrida = buscarCorridaPeloId(corridaId);
        if (!corrida.getStatusCorrida().equals(StatusCorrida.INICIADA)) {
            throw new IllegalStateException("Não é possivel finalizar uma corrida que não foi iniciada!");
        }
        corrida.setStatusCorrida(StatusCorrida.FINALIZADA);
        corridaRepository.save(corrida);
    }

    private Corrida buscarCorridaPeloId(Long corridaId) {
        var corrida = corridaRepository.findByCorridaId(corridaId);
        if (corrida == null) {
            throw new EntityNotFoundException("Corrida não encontrada com ID: " + corridaId);
        }
        return corrida;
    }
}
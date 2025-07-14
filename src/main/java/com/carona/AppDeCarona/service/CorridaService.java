package com.carona.AppDeCarona.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carona.AppDeCarona.controller.dto.corrida.AvaliarMotoristaDto;
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

    public void finalizarCorrida(Long corridaId) {
        var corrida = buscarCorridaPeloId(corridaId);
        if (!corrida.getStatusCorrida().equals(StatusCorrida.INICIADA)) {
            throw new IllegalStateException("Não é possivel finalizar uma corrida que não foi iniciada!");
        }
        corrida.setStatusCorrida(StatusCorrida.FINALIZADA);
        corridaRepository.save(corrida);
    }

    public void avaliarMotorista(AvaliarMotoristaDto dto, Long corridaId) {
        var corrida = buscarCorridaPeloId(corridaId);
        if (!corrida.getStatusCorrida().equals(StatusCorrida.FINALIZADA)) {
            throw new IllegalStateException("Não é possivel avaliar um motorista sem que a corrida esteja finalizada!");
        }
        if (dto.nota() < 0 || dto.nota() > 5) {
            throw new IllegalArgumentException("A avaliação deve ser entre 0 e 5!");
        }
        var motorista = corrida.getMotorista();
        double mediaAnterior = motorista.getAvaliacao();
        int quantidadeAnterior = motorista.getQuantidadeAvaliacoes();
        double novaMedia = ((mediaAnterior * quantidadeAnterior) + dto.nota()) / (quantidadeAnterior + 1);
        motorista.setAvaliacao(novaMedia);
        motorista.setQuantidadeAvaliacoes(quantidadeAnterior + 1);
        usuarioRepository.save(motorista);
    }

    private Corrida buscarCorridaPeloId(Long corridaId) {
        var corrida = corridaRepository.findByCorridaId(corridaId);
        if (corrida == null) {
            throw new EntityNotFoundException("Corrida não encontrada com ID: " + corridaId);
        }
        return corrida;
    }

    public Corrida entrarNaCorrida(Long corridaId, Long usuarioId){
        var corrida = corridaRepository.findByCorridaId(corridaId);
        var passageiro = usuarioRepository.findByUsuarioId(usuarioId);
        if (verificarCapacidade(corridaId)) {
            corrida.setCapacidade(corrida.getCapacidade() - 1);
            corrida.addPassageiro(passageiro);
            passageiro.setCorrida(corrida);
        }
        
        corridaRepository.save(corrida);
        return corrida;

    }

    private boolean verificarCapacidade(Long corridaId){
        var corrida = corridaRepository.findByCorridaId(corridaId);
        if (corrida.getCapacidade() == 0) {
            throw new IllegalStateException("Corrida sem disponibilidade!");
        }

        return true;
    }

}
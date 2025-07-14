package com.carona.AppDeCarona.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.carona.AppDeCarona.controller.dto.corrida.CreateCorridaDto;
import com.carona.AppDeCarona.entity.enums.StatusCorrida;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "corrida")
public class Corrida {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long corridaId;


    @NotBlank
    private String localDeOrigem;

    @NotBlank
    private String localDeDestino;

    private double duracao;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Usuario motorista;

    @OneToMany(mappedBy = "corrida")
    @JsonManagedReference
    private List<Usuario> passageiros = new ArrayList<>();

    private LocalDate dataDeSaida;

    private LocalTime horarioDeSaida;

    private LocalDateTime previsaoDeChegada;

    @Enumerated(EnumType.STRING)
    private StatusCorrida statusCorrida;

    private int capacidade;

    public Corrida() {}

    public Corrida(CreateCorridaDto dto, Usuario motorista){
        this.localDeDestino = dto.localDeDestino();
        this.localDeOrigem = dto.localDeOrigem();
        this.motorista = motorista;
        this.horarioDeSaida = dto.horarioDeSaida();
        this.dataDeSaida = dto.dataDeSaida();
        this.capacidade = dto.capacidade();
    }

    public void addPassageiro(Usuario usuario){
        this.passageiros.add(usuario);
    }

    public Long getCorridaId() {
        return corridaId;
    }

    public void setCorridaId(Long corridaId) {
        this.corridaId = corridaId;
    }

    public String getLocalDeOrigem() {
        return localDeOrigem;
    }

    public void setLocalDeOrigem(String localDeOrigem) {
        this.localDeOrigem = localDeOrigem;
    }

    public String getLocalDeDestino() {
        return localDeDestino;
    }

    public void setLocalDeDestino(String localDeDestino) {
        this.localDeDestino = localDeDestino;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public Usuario getMotorista() {
        return motorista;
    }

    public void setMotorista(Usuario motorista) {
        this.motorista = motorista;
    }

    public LocalTime getHorarioDeSaida() {
        return horarioDeSaida;
    }

    public void setHorarioDeSaida(LocalTime horarioDeSaida) {
        this.horarioDeSaida = horarioDeSaida;
    }

    public LocalDateTime getPrevisaoDeChegada() {
        return previsaoDeChegada;
    }

    public void setPrevisaoDeChegada(LocalDateTime previsaoDeChegada) {
        this.previsaoDeChegada = previsaoDeChegada;
    }

    public StatusCorrida getStatusCorrida() {
        return statusCorrida;
    }

    public void setStatusCorrida(StatusCorrida statusCorrida) {
        this.statusCorrida = statusCorrida;
    }

    public LocalDate getDataDeSaida() {
        return dataDeSaida;
    }

    public void setDataDeSaida(LocalDate dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Usuario> getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(List<Usuario> passageiros) {
        this.passageiros = passageiros;
    }

    
    

    
}

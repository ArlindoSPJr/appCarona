package com.carona.AppDeCarona.controller.dto.OpenRoute;

public class DistanciaResponse {
    private double distancia; // em metros
    private double duracao;   // em segundos

    public DistanciaResponse(double distancia, double duracao) {
        this.distancia = distancia;
        this.duracao = duracao;
    }

    public double getDistancia() { return distancia; }
    public void setDistancia(double distancia) { this.distancia = distancia; }

    public double getDuracao() { return duracao; }
    public void setDuracao(double duracao) { this.duracao = duracao; }
}

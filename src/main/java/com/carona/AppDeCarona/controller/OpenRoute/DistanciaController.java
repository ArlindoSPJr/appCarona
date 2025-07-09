package com.carona.AppDeCarona.controller.OpenRoute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.carona.AppDeCarona.controller.dto.OpenRoute.DistanciaRequest;
import com.carona.AppDeCarona.controller.dto.OpenRoute.DistanciaResponse;
import com.carona.AppDeCarona.service.OpenRouteService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/distancia")
public class DistanciaController {

    @Autowired
    private OpenRouteService openRouteService;

    @PostMapping
    public Mono<DistanciaResponse> getDistancia(@RequestBody DistanciaRequest request) {
        return openRouteService.calcularDistanciaPorEndereco(request.getOrigem(), request.getDestino());
    }
}
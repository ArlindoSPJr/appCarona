package com.carona.AppDeCarona.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.carona.AppDeCarona.controller.dto.OpenRoute.GeocodeResponse;
import com.carona.AppDeCarona.entity.Coordenadas;

import reactor.core.publisher.Mono;

@Service
public class GeocodingService {

    @Value("${openroute.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openrouteservice.org/geocode/search")
            .build();

    public Mono<Coordenadas> getCoordenadas(String endereco) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey)
                        .queryParam("text", endereco)
                        .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class)
                .map(response -> {
                    double[] coords = response.getFeatures().get(0).getGeometry().getCoordinates();
                    return new Coordenadas(coords[1], coords[0]); // lat, lon
                });
    }
}


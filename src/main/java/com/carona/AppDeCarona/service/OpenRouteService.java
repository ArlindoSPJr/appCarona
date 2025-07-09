package com.carona.AppDeCarona.service;

import com.carona.AppDeCarona.controller.dto.OpenRoute.DirectionsRequest;
import com.carona.AppDeCarona.controller.dto.OpenRoute.DistanciaResponse;
import com.carona.AppDeCarona.controller.dto.OpenRoute.OpenRouteResponse;
import com.carona.AppDeCarona.entity.Coordenadas;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenRouteService {

    private final WebClient webClient;
    private final GeocodingService geocodingService;

    public OpenRouteService(WebClient.Builder webClientBuilder,
            GeocodingService geocodingService,
            @Value("${openroute.api.key}") String apiKey) {
        this.geocodingService = geocodingService;
        this.webClient = webClientBuilder
                .baseUrl("https://api.openrouteservice.org")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();
    }

    public Mono<DistanciaResponse> calcularDistanciaPorEndereco(String origem, String destino) {
        return Mono.zip(
                geocodingService.getCoordenadas(origem),
                geocodingService.getCoordenadas(destino))
                .flatMap(tuple -> {
                    Coordenadas orig = tuple.getT1();
                    Coordenadas dest = tuple.getT2();

                    // DTO de request mais seguro que JSON manual
                    DirectionsRequest req = new DirectionsRequest();
                    req.setCoordinates(List.of(
                            List.of(orig.getLongitude(), orig.getLatitude()),
                            List.of(dest.getLongitude(), dest.getLatitude())));

                    return webClient.post()
                            .uri("/v2/directions/driving-car/geojson")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(req)
                            .exchangeToMono(response -> {
                                if (response.statusCode().isError()) {
                                    return response.bodyToMono(String.class)
                                            .flatMap(err -> Mono.error(new RuntimeException("ORS error: " + err)));
                                }
                                return response.bodyToMono(OpenRouteResponse.class);
                            })
                            .map(this::toDistanciaResponse);
                });
    }

    private DistanciaResponse toDistanciaResponse(OpenRouteResponse resp) {
        var feat = resp.getFeatures().get(0);
        var seg = feat.getProperties().getSegments().get(0);
        return new DistanciaResponse(seg.getDistance(), seg.getDuration());
    }
}

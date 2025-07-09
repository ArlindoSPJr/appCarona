package com.carona.AppDeCarona.controller.dto.OpenRoute;

import java.util.List;

public class DirectionsRequest {

    /**
     * Lista de pares [longitude, latitude].
     * Exemplo:
     *   [
     *     [-46.57421, -23.49985],
     *     [-46.57405, -23.50004]
     *   ]
     */
    private List<List<Double>> coordinates;

    public DirectionsRequest() {
    }

    public DirectionsRequest(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

}

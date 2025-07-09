package com.carona.AppDeCarona.entity;

public class Coordenadas {
    private double latitude;
    private double longitude;

    public Coordenadas(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}


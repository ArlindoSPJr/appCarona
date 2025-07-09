package com.carona.AppDeCarona.controller.dto.OpenRoute;

import java.util.List;

public class GeocodeResponse {
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public static class Feature {
        private Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }
    }

    public static class Geometry {
        private double[] coordinates;

        public double[] getCoordinates() {
            return coordinates;
        }
    }
}


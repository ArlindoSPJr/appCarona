package com.carona.AppDeCarona.controller.dto.OpenRoute;

import java.util.List;

public class OpenRouteResponse {
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public static class Feature {
        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }

    public static class Properties {
        private List<Segment> segments;

        public List<Segment> getSegments() {
            return segments;
        }

        public void setSegments(List<Segment> segments) {
            this.segments = segments;
        }
    }

    public static class Segment {
        private double distance; // metros
        private double duration; // segundos

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }
    }

    public static class Summary {
        private double distance;
        private double duration;
        public double getDistance() {
            return distance;
        }
        public void setDistance(double distance) {
            this.distance = distance;
        }
        public double getDuration() {
            return duration;
        }
        public void setDuration(double duration) {
            this.duration = duration;
        }
        
    }

}

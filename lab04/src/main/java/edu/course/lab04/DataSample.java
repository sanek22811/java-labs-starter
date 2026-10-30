package edu.course.lab04;

import java.util.Arrays;

public final class DataSample {

    private final String label;
    private final double[] features;

    public DataSample(String label, double[] features) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label cannot be null or blank");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features cannot be null or empty");
        }
        this.label = label;
        this.features = features.clone();
    }

    public String getLabel() {
        return label;
    }

    public double[] getFeatures() {
        return features.clone();
    }

    @Override
    public String toString() {
        return "DataSample{label='" + label + "', features=" + Arrays.toString(features) + "}";
    }
}

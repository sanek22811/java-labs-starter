package edu.course.lab03;

public final class MinMaxNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Values array cannot be null or empty");
        }

        double min = values[0];
        double max = values[0];
        for (double v : values) {
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }

        double[] result = new double[values.length];
        double range = max - min;

        for (int i = 0; i < values.length; i++) {
            if (Double.compare(range, 0.0) == 0) {
                result[i] = 0.0;
            } else {
                result[i] = (values[i] - min) / range;
            }
        }

        return result;
    }
}

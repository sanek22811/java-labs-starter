package edu.course.lab03;

public final class FeatureProcessor {

    private final NormalizationStrategy strategy;

    public FeatureProcessor(NormalizationStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public double[] process(double[] features) {
        if (features == null) {
            throw new IllegalArgumentException("Features array cannot be null");
        }
        return this.strategy.normalize(features);
    }
}

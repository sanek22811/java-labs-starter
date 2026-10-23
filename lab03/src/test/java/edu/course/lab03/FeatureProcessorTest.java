package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FeatureProcessorTest {

    @Test
    void minMaxNormalizationScalesCorrectly() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] input = {10.0, 20.0, 30.0};
        double[] expected = {0.0, 0.5, 1.0};

        double[] actual = processor.process(input);

        assertArrayEquals(expected, actual, 1e-9);
    }

    @Test
    void minMaxNormalizationHandlesConstantArray() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] input = {5.0, 5.0, 5.0};
        double[] expected = {0.0, 0.0, 0.0};

        assertArrayEquals(expected, processor.process(input), 1e-9);
    }

    @Test
    void meanCenteringNormalizationCentersCorrectly() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] input = {1.0, 2.0, 6.0}; // mean = 3.0
        double[] expected = {-2.0, -1.0, 3.0};

        double[] actual = processor.process(input);

        assertArrayEquals(expected, actual, 1e-9);
    }

    @Test
    void processorRejectsNullOrEmptyInputs() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());

        assertThrows(IllegalArgumentException.class, () -> processor.process(null));
        assertThrows(IllegalArgumentException.class, () -> processor.process(new double[0]));
    }

    @Test
    void processorRejectsNullStrategy() {
        assertThrows(IllegalArgumentException.class, () -> new FeatureProcessor(null));
    }

    @Test
    void doesNotMutateOriginalArray() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] original = {10.0, 50.0, 100.0};
        double[] copy = original.clone();

        processor.process(original);

        assertArrayEquals(copy, original, 1e-9);
    }

    @Test
    void supportsRuntimePolymorphicStrategySubstitution() {
        double[] data = {10.0, 20.0, 30.0};

        FeatureProcessor minMaxProcessor = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor meanProcessor = new FeatureProcessor(new MeanCenteringNormalization());

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, minMaxProcessor.process(data), 1e-9);
        assertArrayEquals(new double[]{-10.0, 0.0, 10.0}, meanProcessor.process(data), 1e-9);
    }
}

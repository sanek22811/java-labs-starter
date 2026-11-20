package edu.course.lab05;

import java.util.List;

public final class LoadResult {

    private final List<DataSample> samples;
    private final List<ValidationError> errors;

    public LoadResult(List<DataSample> samples, List<ValidationError> errors) {
        if (samples == null) {
            throw new IllegalArgumentException("Samples list cannot be null");
        }
        if (errors == null) {
            throw new IllegalArgumentException("Errors list cannot be null");
        }
        this.samples = List.copyOf(samples);
        this.errors = List.copyOf(errors);
    }

    public List<DataSample> getSamples() {
        return samples;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}

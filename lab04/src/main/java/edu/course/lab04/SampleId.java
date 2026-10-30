package edu.course.lab04;

import java.util.Objects;

public final class SampleId {

    private final String datasetName;
    private final int sampleIndex;

    public SampleId(String datasetName, int sampleIndex) {
        if (datasetName == null || datasetName.isBlank()) {
            throw new IllegalArgumentException("Dataset name cannot be null or blank");
        }
        if (sampleIndex < 0) {
            throw new IllegalArgumentException("Sample index cannot be negative");
        }
        this.datasetName = datasetName;
        this.sampleIndex = sampleIndex;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public int getSampleIndex() {
        return sampleIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SampleId other = (SampleId) o;
        return sampleIndex == other.sampleIndex && Objects.equals(datasetName, other.datasetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasetName, sampleIndex);
    }

    @Override
    public String toString() {
        return "SampleId{datasetName='" + datasetName + "', sampleIndex=" + sampleIndex + "}";
    }
}

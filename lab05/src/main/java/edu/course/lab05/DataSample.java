package edu.course.lab05;

import java.util.Objects;

public final class DataSample {

    private final String id;
    private final String label;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public DataSample(String id, String label, String source, int featureCount, SampleStatus status) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Sample id cannot be null or blank");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Sample label cannot be null or blank");
        }
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Sample source cannot be null or blank");
        }
        if (featureCount <= 0) {
            throw new IllegalArgumentException("Feature count must be positive");
        }
        if (status == null) {
            throw new IllegalArgumentException("Sample status cannot be null");
        }

        this.id = id;
        this.label = label;
        this.source = source;
        this.featureCount = featureCount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public int getFeatureCount() {
        return featureCount;
    }

    public SampleStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSample that = (DataSample) o;
        return featureCount == that.featureCount
                && Objects.equals(id, that.id)
                && Objects.equals(label, that.label)
                && Objects.equals(source, that.source)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, source, featureCount, status);
    }

    @Override
    public String toString() {
        return "DataSample{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", source='" + source + '\'' +
                ", featureCount=" + featureCount +
                ", status=" + status +
                '}';
    }
}
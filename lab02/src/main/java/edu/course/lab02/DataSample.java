package edu.course.lab02;

public final class DataSample {

    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, SampleStatus status, double[] features) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Sample ID cannot be null or blank");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Sample label cannot be null or blank");
        }
        if (status == null) {
            throw new IllegalArgumentException("Sample status cannot be null");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features array cannot be null or empty");
        }

        this.id = id;
        this.label = label;
        this.status = status;
        this.features = features.clone();
    }

    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null");
        }
        this.status = newStatus;
    }

    public boolean isReady() {
        return this.status == SampleStatus.READY;
    }

    public double calculateMeanFeature() {
        double sum = 0.0;
        for (double feature : this.features) {
            sum += feature;
        }
        return sum / this.features.length;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }

    public double[] getFeatures() {
        return this.features.clone();
    }
}
package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;

public final class SampleRegistry {

    private final Map<SampleId, DataSample> storage = new HashMap<>();

    public void put(SampleId id, DataSample sample) {
        if (id == null) {
            throw new IllegalArgumentException("SampleId cannot be null");
        }
        if (sample == null) {
            throw new IllegalArgumentException("DataSample cannot be null");
        }
        this.storage.put(id, sample);
    }

    public DataSample get(SampleId id) {
        if (id == null) {
            throw new IllegalArgumentException("SampleId cannot be null");
        }
        return this.storage.get(id);
    }

    public boolean remove(SampleId id) {
        if (id == null) {
            throw new IllegalArgumentException("SampleId cannot be null");
        }
        return this.storage.remove(id) != null;
    }

    public boolean containsKey(SampleId id) {
        if (id == null) {
            throw new IllegalArgumentException("SampleId cannot be null");
        }
        return this.storage.containsKey(id);
    }

    public int size() {
        return this.storage.size();
    }

    public boolean isEmpty() {
        return this.storage.isEmpty();
    }
}

package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataSampleTest {

    @Test
    void createsSampleSuccessfullyWithValidData() {
        double[] features = {1.0, 2.0, 3.0};
        DataSample sample = new DataSample("SMP-001", "Cat", SampleStatus.RAW, features);

        assertEquals("SMP-001", sample.getId());
        assertEquals("Cat", sample.getLabel());
        assertEquals(SampleStatus.RAW, sample.getStatus());
        assertEquals(2.0, sample.calculateMeanFeature(), 1e-9);
        assertFalse(sample.isReady());
    }

    @Test
    void constructorRejectsInvalidId() {
        double[] features = {1.0};
        assertThrows(IllegalArgumentException.class, () -> new DataSample(null, "Label", SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("", "Label", SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("   ", "Label", SampleStatus.RAW, features));
    }

    @Test
    void constructorRejectsInvalidLabel() {
        double[] features = {1.0};
        assertThrows(IllegalArgumentException.class, () -> new DataSample("ID", null, SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("ID", "", SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("ID", "   ", SampleStatus.RAW, features));
    }

    @Test
    void constructorRejectsNullStatus() {
        double[] features = {1.0};
        assertThrows(IllegalArgumentException.class, () -> new DataSample("ID", "Label", null, features));
    }

    @Test
    void constructorRejectsNullOrEmptyFeatures() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample("ID", "Label", SampleStatus.RAW, null));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("ID", "Label", SampleStatus.RAW, new double[0]));
    }

    @Test
    void changeStatusUpdatesStatus() {
        DataSample sample = new DataSample("SMP-002", "Dog", SampleStatus.RAW, new double[]{0.5});
        sample.changeStatus(SampleStatus.PREPROCESSED);
        assertEquals(SampleStatus.PREPROCESSED, sample.getStatus());
        assertFalse(sample.isReady());

        sample.changeStatus(SampleStatus.READY);
        assertEquals(SampleStatus.READY, sample.getStatus());
        assertTrue(sample.isReady());
    }

    @Test
    void changeStatusRejectsNull() {
        DataSample sample = new DataSample("SMP-003", "Bird", SampleStatus.RAW, new double[]{0.1});
        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
    }

    @Test
    void calculateMeanFeatureReturnsCorrectAverage() {
        DataSample sample = new DataSample("SMP-004", "Fish", SampleStatus.RAW, new double[]{-2.0, 4.0, 10.0});
        assertEquals(4.0, sample.calculateMeanFeature(), 1e-9);
    }

    @Test
    void protectsFeaturesFromExternalMutationViaConstructor() {
        double[] original = {10.0, 20.0};
        DataSample sample = new DataSample("SMP-005", "Car", SampleStatus.RAW, original);

        original[0] = 999.0;

        assertEquals(10.0, sample.getFeatures()[0], 1e-9);
    }

    @Test
    void protectsFeaturesFromExternalMutationViaGetter() {
        DataSample sample = new DataSample("SMP-006", "Plane", SampleStatus.RAW, new double[]{5.0, 15.0});

        double[] extracted = sample.getFeatures();
        extracted[0] = 888.0;

        assertEquals(5.0, sample.getFeatures()[0], 1e-9);
    }
}

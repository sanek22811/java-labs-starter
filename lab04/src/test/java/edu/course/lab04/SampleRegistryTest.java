package edu.course.lab04;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleRegistryTest {

    @Test
    void findsValueByLogicallyEqualKeyInstances() {
        SampleRegistry registry = new SampleRegistry();
        SampleId key1 = new SampleId("MNIST", 42);
        SampleId key2 = new SampleId("MNIST", 42);

        DataSample sample = new DataSample("Digit 4", new double[]{0.0, 1.0, 0.5});
        registry.put(key1, sample);

        assertEquals(key1, key2);
        assertEquals(key1.hashCode(), key2.hashCode());
        assertTrue(registry.containsKey(key2));
        assertEquals("Digit 4", registry.get(key2).getLabel());
    }

    @Test
    void overwriteExistingKeyReplacesValueWithoutIncreasingSize() {
        SampleRegistry registry = new SampleRegistry();
        SampleId key1 = new SampleId("CIFAR", 1);
        SampleId key2 = new SampleId("CIFAR", 1);

        DataSample firstSample = new DataSample("Airplane", new double[]{1.0, 2.0});
        DataSample updatedSample = new DataSample("Bird", new double[]{3.0, 4.0});

        registry.put(key1, firstSample);
        assertEquals(1, registry.size());

        registry.put(key2, updatedSample);
        assertEquals(1, registry.size());
        assertEquals("Bird", registry.get(key1).getLabel());
    }

    @Test
    void handlesHashCollisionCorrectlyForUnequalKeys() {
        // Учебный ключ с намеренно жестко заданной коллизией (всегда hashCode = 42)
        class CollidingKey {
            private final String id;

            CollidingKey(String id) {
                this.id = id;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                CollidingKey that = (CollidingKey) o;
                return Objects.equals(id, that.id);
            }

            @Override
            public int hashCode() {
                return 42; // Искусственная 100% коллизия
            }
        }

        Map<CollidingKey, String> map = new HashMap<>();
        CollidingKey k1 = new CollidingKey("sampleA");
        CollidingKey k2 = new CollidingKey("sampleB");

        assertNotEquals(k1, k2);
        assertEquals(k1.hashCode(), k2.hashCode());

        map.put(k1, "Data A");
        map.put(k2, "Data B");

        assertEquals(2, map.size());
        assertEquals("Data A", map.get(k1));
        assertEquals("Data B", map.get(k2));
    }

    @Test
    void equalsReturnsFalseForNullAndOtherTypes() {
        SampleId key = new SampleId("ImageNet", 100);

        assertFalse(key.equals(null));
        assertFalse(key.equals("ImageNet_100"));
        assertFalse(key.equals(new Object()));
        assertTrue(key.equals(key));
    }

    @Test
    void demonstratesMutableKeyProblemInHashMap() {
        // Учебный изменяемый ключ исключительно для эксперимента
        class MutableKey {
            private String name;

            MutableKey(String name) {
                this.name = name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                MutableKey that = (MutableKey) o;
                return Objects.equals(name, that.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name);
            }
        }

        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey("initial_name");
        map.put(key, "Important Payload");

        assertEquals("Important Payload", map.get(key));

        // Мутируем поле ключа ПОСЛЕ помещения в HashMap
        key.setName("mutated_name");

        // Объект потерян в HashMap: поиск по тому же самому экземпляру возвращает null!
        assertNull(map.get(key));
        assertFalse(map.containsKey(key));
    }

    @Test
    void basicCrudOperationsWorkAsExpected() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id = new SampleId("VOC", 10);
        DataSample sample = new DataSample("Person", new double[]{0.9});

        assertTrue(registry.isEmpty());
        registry.put(id, sample);
        assertFalse(registry.isEmpty());
        assertEquals(1, registry.size());

        assertTrue(registry.remove(id));
        assertEquals(0, registry.size());
        assertNull(registry.get(id));
        assertFalse(registry.remove(id));
    }

    @Test
    void rejectsNullArguments() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id = new SampleId("Test", 1);
        DataSample sample = new DataSample("Label", new double[]{1.0});

        assertThrows(IllegalArgumentException.class, () -> registry.put(null, sample));
        assertThrows(IllegalArgumentException.class, () -> registry.put(id, null));
        assertThrows(IllegalArgumentException.class, () -> registry.get(null));
        assertThrows(IllegalArgumentException.class, () -> registry.remove(null));
        assertThrows(IllegalArgumentException.class, () -> registry.containsKey(null));
    }
}

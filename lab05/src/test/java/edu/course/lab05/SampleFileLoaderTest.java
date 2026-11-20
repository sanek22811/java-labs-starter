package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleFileLoaderTest {

    @TempDir
    Path tempDir;

    private final SampleFileLoader loader = new SampleFileLoader();

    @Test
    void loadsValidFileSuccessfully() throws IOException {
        Path file = tempDir.resolve("valid.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;256;RAW"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(2, result.getSamples().size());
        assertEquals(new DataSample("S-101", "cat", "camera-1", 128, SampleStatus.READY), result.getSamples().get(0));
        assertEquals(new DataSample("S-102", "dog", "camera-2", 256, SampleStatus.RAW), result.getSamples().get(1));
    }

    @Test
    void supportsCyrillicAndUnicodeTextInUtf8() throws IOException {
        Path file = tempDir.resolve("unicode.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "С-001;кошка;камера-север-1;64;PREPROCESSED"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertFalse(result.hasErrors());
        assertEquals(1, result.getSamples().size());
        assertEquals("кошка", result.getSamples().get(0).getLabel());
        assertEquals("камера-север-1", result.getSamples().get(0).getSource());
    }

    @Test
    void reportsErrorOnEmptyField() throws IOException {
        Path file = tempDir.resolve("empty_field.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "S-101;;camera-1;128;READY"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.hasErrors());
        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("label", result.getErrors().get(0).getField());
    }

    @Test
    void reportsErrorOnInvalidNumber() throws IOException {
        Path file = tempDir.resolve("bad_number.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "S-101;cat;camera-1;not_a_number;READY"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.hasErrors());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(0).getField());
    }

    @Test
    void reportsErrorOnUnknownStatus() throws IOException {
        Path file = tempDir.resolve("bad_status.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "S-101;cat;camera-1;128;ARCHIVED"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.hasErrors());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void reportsErrorOnWrongColumnCount() throws IOException {
        Path file = tempDir.resolve("wrong_columns.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "S-101;cat;camera-1;128"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.hasErrors());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("row", result.getErrors().get(0).getField());
    }

    @Test
    void processesValidAndInvalidRowsTogether() throws IOException {
        Path file = tempDir.resolve("mixed.csv");
        Files.write(file, List.of(
                "id;label;source;featureCount;status",
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;abc;READY",
                "",
                "S-103;bird;camera-3;64;UNKNOWN_STATUS",
                "S-104;fish;camera-4;32;REJECTED"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(2, result.getSamples().size());
        assertEquals("S-101", result.getSamples().get(0).getId());
        assertEquals("S-104", result.getSamples().get(1).getId());

        assertEquals(2, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(0).getField());
        assertEquals(5, result.getErrors().get(1).getLineNumber());
        assertEquals("status", result.getErrors().get(1).getField());
    }

    @Test
    void throwsDataLoadingExceptionWithCauseWhenFileMissing() {
        Path missingFile = tempDir.resolve("non_existent.csv");

        DataLoadingException ex = assertThrows(DataLoadingException.class, () -> loader.load(missingFile));

        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof IOException);
    }

    @Test
    void throwsDataLoadingExceptionOnInvalidHeader() throws IOException {
        Path file = tempDir.resolve("bad_header.csv");
        Files.write(file, List.of(
                "wrong;header;columns",
                "S-101;cat;camera-1;128;READY"
        ), StandardCharsets.UTF_8);

        assertThrows(DataLoadingException.class, () -> loader.load(file));
    }
}

package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SampleFileLoader {

    private static final String EXPECTED_HEADER = "id;label;source;featureCount;status";
    private static final int EXPECTED_COLUMNS = 5;

    public LoadResult load(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }

        List<DataSample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new DataLoadingException("File is empty: " + path, null);
            }
            if (!EXPECTED_HEADER.equals(headerLine.trim())) {
                throw new DataLoadingException(
                        "Invalid header in " + path + ": expected '" + EXPECTED_HEADER + "', got '" + headerLine + "'",
                        null
                );
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                parseLine(line, lineNumber, samples, errors);
            }

        } catch (IOException e) {
            throw new DataLoadingException("Cannot read " + path, e);
        }

        return new LoadResult(samples, errors);
    }

    private void parseLine(String line, int lineNumber, List<DataSample> samples, List<ValidationError> errors) {
        String[] parts = line.split(";", -1);
        if (parts.length != EXPECTED_COLUMNS) {
            errors.add(new ValidationError(
                    lineNumber,
                    "row",
                    "Expected " + EXPECTED_COLUMNS + " columns, got " + parts.length
            ));
            return;
        }

        String id = parts[0].trim();
        String label = parts[1].trim();
        String source = parts[2].trim();
        String featureCountRaw = parts[3].trim();
        String statusRaw = parts[4].trim();

        if (id.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "id", "ID cannot be empty"));
            return;
        }
        if (label.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "label", "Label cannot be empty"));
            return;
        }
        if (source.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "source", "Source cannot be empty"));
            return;
        }
        if (featureCountRaw.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Feature count cannot be empty"));
            return;
        }
        if (statusRaw.isEmpty()) {
            errors.add(new ValidationError(lineNumber, "status", "Status cannot be empty"));
            return;
        }

        int featureCount;
        try {
            featureCount = Integer.parseInt(featureCountRaw);
        } catch (NumberFormatException e) {
            errors.add(new ValidationError(
                    lineNumber,
                    "featureCount",
                    "Invalid integer value: '" + featureCountRaw + "'"
            ));
            return;
        }

        if (featureCount <= 0) {
            errors.add(new ValidationError(
                    lineNumber,
                    "featureCount",
                    "Feature count must be positive, got: " + featureCount
            ));
            return;
        }

        SampleStatus status;
        try {
            status = SampleStatus.valueOf(statusRaw);
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationError(
                    lineNumber,
                    "status",
                    "Unknown status: '" + statusRaw + "'"
            ));
            return;
        }

        DataSample sample = new DataSample(id, label, source, featureCount, status);
        samples.add(sample);
    }
}

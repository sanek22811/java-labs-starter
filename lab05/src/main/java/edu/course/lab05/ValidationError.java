package edu.course.lab05;

import java.util.Objects;

public final class ValidationError {

    private final int lineNumber;
    private final String field;
    private final String message;

    public ValidationError(int lineNumber, String field, String message) {
        if (lineNumber < 1) {
            throw new IllegalArgumentException("Line number must be positive");
        }
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("Field cannot be null or blank");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
        this.lineNumber = lineNumber;
        this.field = field;
        this.message = message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return lineNumber == that.lineNumber
                && Objects.equals(field, that.field)
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, field, message);
    }

    @Override
    public String toString() {
        return "ValidationError{lineNumber=" + lineNumber +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

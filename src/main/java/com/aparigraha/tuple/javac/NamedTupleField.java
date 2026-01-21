package com.aparigraha.tuple.javac;

public record NamedTupleField(
        int index,
        String name,
        Class<?> type
) {
    public NamedTupleField {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative: " + index);
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
    }
}

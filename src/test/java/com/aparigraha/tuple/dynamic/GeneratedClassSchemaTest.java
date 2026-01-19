package com.aparigraha.tuple.dynamic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GeneratedClassSchemaTest {
    @Test
    void shouldReturnCompleteClassName() {
        var tupleSchema = new GeneratedClassSchema("package", "className", "Tuple code");
        assertEquals("package.className", tupleSchema.completeClassName());
    }
}
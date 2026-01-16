package com.aparigraha.tuple.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TupleSchemaTest {
    @Test
    void shouldReturnCompleteClassName() {
        var tupleSchema = new TupleSchema("package", "className", "Tuple code");
        assertEquals("package.className", tupleSchema.completeClassName());
    }
}
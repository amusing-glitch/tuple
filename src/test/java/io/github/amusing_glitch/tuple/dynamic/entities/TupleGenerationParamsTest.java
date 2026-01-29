package io.github.amusing_glitch.tuple.dynamic.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TupleGenerationParamsTest {
    @Test
    void shouldCreateTupleFieldsForNumberedTuples() {
        var tupleGenerationParams = new TupleGenerationParams(
                "io.github.amusing_glitch.tuples",
                "Tuple3",
                "item",
                3
        );

        assertEquals("io.github.amusing_glitch.tuples", tupleGenerationParams.packageName());
        assertEquals("Tuple3", tupleGenerationParams.className());
        assertEquals(List.of("item0", "item1", "item2"), tupleGenerationParams.fields());
    }
}
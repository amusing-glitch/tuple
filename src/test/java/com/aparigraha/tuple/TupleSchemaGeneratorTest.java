package com.aparigraha.tuple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TupleSchemaGeneratorTest {
    @Test
    void shouldCreateTupleClassFor3Elements() {
        var tupleGenerator = new TupleSchemaGenerator(
                "com.aparigraha.tuple",
                "Tuple",
                "T",
                "item"
        );

        var tupleSchema = tupleGenerator.generate(3);

        assertEquals(
                """
                package com.aparigraha.tuple;
                
                public record Tuple3<T0, T1, T2> (
                \tT0 item0,
                \tT1 item1,
                \tT2 item2
                ) {}
                """,
                tupleSchema.textContent()
        );
        assertEquals("Tuple3", tupleSchema.className());
    }
}
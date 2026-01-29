package io.github.amusing_glitch.tuple.dynamic.entities;

import io.github.amusing_glitch.tuple.dynamic.GeneratedClassSchema;
import io.github.amusing_glitch.tuple.dynamic.templates.PebbleTemplateProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class NamedTupleGeneratorTest {
    @Test
    void shouldCreateTheRespectiveNamedTupleClass() throws IOException {
        var namedTupleGenerator = new NamedTupleGenerator(
                new PebbleTemplateProcessor("templates")
        );


        var expected = """
        package com.example;
        
        
        public record Student (
            java.lang.String Name, int age
        ) {
        }
        """.trim();
        GeneratedClassSchema schema = namedTupleGenerator.generate(
                new NamedTupleGenerationParams(
                        "com.example",
                        "Student",
                        Set.of(
                                new NamedTupleGenerationFieldParams(1, "int", "age"),
                                new NamedTupleGenerationFieldParams(0, "java.lang.String", "Name")
                        )
                )
        );
        assertEquals(expected, schema.javaCode());
        assertEquals("com.example", schema.packageName());
        assertEquals("Student", schema.className());
    }
}
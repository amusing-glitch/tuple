package com.aparigraha.tuple.dynamic;

import com.aparigraha.tuple.templates.PebbleTemplateProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ZipperMethodGeneratorTest {
    @Test
    void shouldGenerateTheZipperDynamicMethod() throws IOException {
        var generator = new ZipperMethodGenerator(
                new PebbleTemplateProcessor("templates")
        );

        System.out.println(generator.generate(3));
    }
}
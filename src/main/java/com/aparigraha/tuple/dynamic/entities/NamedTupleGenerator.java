package com.aparigraha.tuple.dynamic.entities;

import com.aparigraha.tuple.dynamic.GeneratedClassSchema;
import com.aparigraha.tuple.dynamic.templates.PebbleTemplateProcessor;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

import static com.aparigraha.tuple.dynamic.templates.JavaTemplate.fields;


public class NamedTupleGenerator {
    private final PebbleTemplateProcessor pebbleTemplateProcessor;

    public NamedTupleGenerator(PebbleTemplateProcessor pebbleTemplateProcessor) {
        this.pebbleTemplateProcessor = pebbleTemplateProcessor;
    }


    public GeneratedClassSchema generate(NamedTupleGenerationParams params) throws IOException {
        return new GeneratedClassSchema(
                params.packageName(),
                params.className(),
                pebbleTemplateProcessor.process(
                        "NamedTuple.peb", templateParams(params)
                )
        );
    }


    private static Map<String, Object> templateParams(NamedTupleGenerationParams params) {
        return Map.of(
                "packageName", params.packageName(),
                "className", params.className(),
                "fields", fields(
                        params.fieldParams().stream()
                                .sorted(Comparator.comparingInt(NamedTupleGenerationFieldParams::index))
                                .map(namedTupleGenerationFieldParams ->
                                        new String[] {
                                                namedTupleGenerationFieldParams.type(),
                                                namedTupleGenerationFieldParams.name()
                                        }
                                )
                                .toArray(String[][]::new)
                )
        );
    }
}

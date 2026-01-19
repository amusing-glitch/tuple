package com.aparigraha.tuple.generator;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import static com.aparigraha.tuple.templates.JavaTemplate.*;


public class TupleGenerator {
    private static final PebbleTemplate template = getTemplate();

    public TupleSchema generate(TupleGenerationParams params) throws IOException {
        Writer writer = new StringWriter();
        template.evaluate(writer, templateParams(params.packageName(), params.className(), params.fields()));
        return new TupleSchema(
                params.packageName(),
                params.className(),
                writer.toString()
        );
    }

    private static Map<String, Object> templateParams(String packageName, String className, List<String> fields) {
        int size = fields.size();
        return Map.of(
                "packageName", packageName,
                "className", className,
                "genericsSequence", genericsSequence(size),
                "fields", fields(fields),
                "equalsMethod", Map.of(
                        "wildcardGenericSequence", wildcardGenericSequence(size),
                        "tupleEqualsParameter", tupleEqualsParameter,
                        "tupleEqualsCondition", tupleEqualsCondition(fields)
                ),
                "zipMethod", Map.of(
                        "zipParameters", zipParameters(size),
                        "objectStreamSequence", objectStreamSequence(size),
                        "listToTupleParameter", listToTupleParameter,
                        "listToTupleSequence", listToTupleSequence(size)
                )
        );
    }

    private static PebbleTemplate getTemplate() {
        ClasspathLoader loader = new ClasspathLoader();
        loader.setPrefix("templates");
        PebbleEngine engine = new PebbleEngine.Builder()
                .strictVariables(true)
                .autoEscaping(false)
                .loader(loader)
                .build();
        return engine.getTemplate("Tuple.peb");
    }
}

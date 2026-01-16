package com.aparigraha.tuple;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TupleGenerator {
    private final String packageName;
    private final String className;
    private final List<String> fields;

    private static final String genericsPrefix = "T";
    private static final String wildcard = "?";
    private static final String tupleEqualsParameter = "that";
    private static final String listToTupleParameter = "zipped";

    private static final PebbleTemplate template = getTemplate();


    public TupleGenerator(String packageName, String className, List<String> fields) {
        this.packageName = packageName;
        this.className = className;
        this.fields = fields;
    }

    public TupleGenerator(String packageName, String className, String fieldPrefix, int fieldCount) {
        this(
                packageName,
                className,
                range(fieldCount).map(index -> fieldPrefix + index).toList()
        );
    }

    public String generate() throws IOException {
        Writer writer = new StringWriter();
        template.evaluate(writer, templateParams());
        return writer.toString();
    }

    private Map<String, Object> templateParams() {
        return Map.of(
                "packageName", packageName,
                "className", className,
                "genericsSequence", genericsSequence(),
                "fields", fields(),
                "equalsMethod", Map.of(
                        "wildcardGenericSequence", wildcardGenericSequence(),
                        "tupleEqualsParameter", tupleEqualsParameter,
                        "tupleEqualsCondition", tupleEqualsCondition()
                ),
                "zipMethod", Map.of(
                        "zipParameters", zipParameters(),
                        "objectStreamSequence", objectStreamSequence(),
                        "listToTupleParameter", listToTupleParameter,
                        "listToTupleSequence", listToTupleSequence()
                )
        );
    }

    private String genericsSequence() {
        return csvOf(generics());
    }

    private Stream<String> generics() {
        return range().map(index -> genericsPrefix + index);
    }

    private String fields() {
        var generics = generics().toList();
        return csvOf(range().map(index -> "%s %s".formatted(generics.get(index), fields.get(index))));
    }

    private String wildcardGenericSequence() {
        return csvOf(Stream.generate(() -> wildcard).limit(fields.size()));
    }

    private String tupleEqualsCondition() {
        return logicalAndOf(
                fields.stream().map(field -> "this.%s == %s.%s".formatted(field, tupleEqualsParameter, field))
        );
    }

    private String zipParameters() {
        return csvOf(range().map(index -> "Stream<T%d> stream%d".formatted(index, index)));
    }

    private String objectStreamSequence() {
        return csvOf(range().map("(Stream<Object>) stream%d"::formatted));
    }

    private String listToTupleSequence() {
        return csvOf(range().map(index -> "(%s%d) %s.get(%d)".formatted(genericsPrefix, index, listToTupleParameter, index)));
    }

    private Stream<Integer> range() {
        return range(fields.size());
    }

    private static Stream<Integer> range(int size) {
        return IntStream.range(0, size).boxed();
    }

    private static String csvOf(Stream<String> items) {
        return items.reduce("%s, %s"::formatted).orElseThrow();
    }

    private static String logicalAndOf(Stream<String> conditions) {
        return conditions.reduce("%s && %s"::formatted).orElseThrow();
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

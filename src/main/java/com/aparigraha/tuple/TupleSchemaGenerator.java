package com.aparigraha.tuple;

import java.util.function.Function;
import java.util.stream.IntStream;


public class TupleSchemaGenerator {
    private final String targetPackage;
    private final String classPrefix;
    private final String genericPrefix;
    private final String fieldPrefix;


    public TupleSchemaGenerator(String targetPackage, String classPrefix, String genericPrefix, String fieldPrefix) {
        this.targetPackage = targetPackage;
        this.classPrefix = classPrefix;
        this.genericPrefix = genericPrefix;
        this.fieldPrefix = fieldPrefix;
    }


    public TupleSchema generate(int size) {
        var textContent = """
        package %s;
        
        public record %s<%s> (
        \t%s
        ) {}
        """.formatted(
                targetPackage,
                simpleClassName(size),
                generateTokenSequence(size, this::genericsTemplate, ", "),
                generateTokenSequence(size, this::fieldTemplate, ",\n\t")
        );

        return new TupleSchema(
                targetPackage,
                simpleClassName(size),
                textContent
        );
    }


    private static String generateTokenSequence(int size, Function<Integer, String> tokenTemplate, String tokenSeparator) {
        return IntStream
                .range(0, size)
                .boxed()
                .map(tokenTemplate)
                .reduce((item1, item2) -> item1 + tokenSeparator + item2)
                .orElseThrow();
    }


    private String simpleClassName(int size) {
        return classPrefix + size;
    }


    private String genericsTemplate(int index) {
        return genericPrefix + index;
    }


    private String fieldTemplate(int index) {
        return genericPrefix + index + " " + fieldPrefix + index;
    }
}

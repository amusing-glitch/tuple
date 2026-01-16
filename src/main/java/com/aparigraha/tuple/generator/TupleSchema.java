package com.aparigraha.tuple.generator;

public record TupleSchema(
        String packageName,
        String className,
        String javaCode
) {
    public String completeClassName() {
        return "%s.%s".formatted(packageName, className);
    }
}

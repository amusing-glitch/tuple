package com.aparigraha.tuple.javac;


public record TupleDefinition(
        String className,
        String methodName,
        int argumentCount
) {
}

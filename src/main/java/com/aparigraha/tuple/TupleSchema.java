package com.aparigraha.tuple;

public record TupleSchema(
        String packageName,
        String className,
        String textContent
) {
    public String fullyQualifiedClassName() {
        return packageName + "." + className;
    }
}

package io.github.amusing_glitch.tuple.dynamic.factories;


@FunctionalInterface
public interface FieldSpec<T> {
    T value(Object fieldName);
}

package com.aparigraha.tuple.dynamic.factories;


@FunctionalInterface
public interface FieldSpec<T> {
    T value(Object fieldName);
}

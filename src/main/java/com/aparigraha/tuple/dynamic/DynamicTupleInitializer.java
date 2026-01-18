package com.aparigraha.tuple.dynamic;


public class DynamicTupleInitializer {
    public static <T> T of(Class<T> tClass, DynamicTupleFieldSpec<?>... fieldSpecs) {
        throw new RuntimeException("Facade method: Operation not permitted");
    }
}

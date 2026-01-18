package com.aparigraha.tuple.dynamic;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class DynamicTuple {
    public static <T> T of(Class<T> tClass, DynamicTupleFieldSpec<?>... fieldSpecs) {
        var constructorOptional = Arrays.stream(tClass.getConstructors()).findFirst();
        if (constructorOptional.isPresent()) {
            var constructor = constructorOptional.get();
            try {
                var args = Arrays.stream(fieldSpecs).map(x -> x.value(null)).toArray();
                return (T) constructor.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Unable to find constructor for the given class");
    }
}

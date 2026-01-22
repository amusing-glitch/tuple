package com.aparigraha.tuple.dynamic.entities;

import java.util.Set;


public record NamedTupleGenerationParams(
        String packageName,
        String className,
        Set<NamedTupleGenerationFieldParams> fieldParams
) {
}

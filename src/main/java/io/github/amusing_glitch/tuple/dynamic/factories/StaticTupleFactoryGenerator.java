package io.github.amusing_glitch.tuple.dynamic.factories;

import io.github.amusing_glitch.tuple.dynamic.templates.PebbleTemplateProcessor;

import java.io.IOException;
import java.util.Map;

import static io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate.*;


public class StaticTupleFactoryGenerator {
    private final PebbleTemplateProcessor pebbleTemplateProcessor;

    public StaticTupleFactoryGenerator(PebbleTemplateProcessor pebbleTemplateProcessor) {
        this.pebbleTemplateProcessor = pebbleTemplateProcessor;
    }

    public String generate(int tupleSize) throws IOException {
        return pebbleTemplateProcessor
                .process(
                        "StaticTupleFactory.peb",
                        Map.of(
                                "genericsSequence", genericsSequence(tupleSize),
                                "className", className(tupleSize),
                                "genericsParameter", genericsParameter(tupleSize),
                                "parameterSequence", parameterSequence(tupleSize)
                        )
                );
    }
}

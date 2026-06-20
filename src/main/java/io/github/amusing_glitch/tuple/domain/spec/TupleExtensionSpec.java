package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleExtensionGenerator;
import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;

public abstract class TupleExtensionSpec<T extends TupleDefinition> extends TupleSpec<T> {
    public final String generateExtensionMethod(T tupleDefinition) {
        return tupleExtensionGenerator().generate(tupleDefinition);
    }

    protected abstract TupleExtensionGenerator<T> tupleExtensionGenerator();
}

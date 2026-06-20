package io.github.amusing_glitch.tuple.domain.codeGenerator;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;

public interface TupleExtensionGenerator<T extends TupleDefinition> {
    String generate(T tupleDefinition);
}

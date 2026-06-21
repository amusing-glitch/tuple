package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;

public interface TupleSpec<S extends Signature<?>, T extends TupleDefinition<?>> {
    T process(S signature);
}

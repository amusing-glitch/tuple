package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.basic.BasicFieldDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicArgument;

public interface BasicTupleSpec<S extends Signature<BasicArgument>, T extends TupleDefinition<BasicFieldDefinition>> extends TupleSpec<S, T> {
}
